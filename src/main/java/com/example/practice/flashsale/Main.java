package com.example.practice.flashsale;

import com.example.practice.flashsale.dto.Order;
import com.example.practice.flashsale.dto.OrderStatusEnum;
import com.example.practice.flashsale.dto.Product;
import com.example.practice.flashsale.service.FlashSale;
import com.example.practice.flashsale.service.FlashSaleImpl;
import com.example.practice.flashsale.service.InventoryService;
import com.example.practice.flashsale.service.InventoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 1. Initialize Services
        InventoryService inventoryService = new InventoryServiceImpl();
        FlashSale flashSaleService = new FlashSaleImpl(inventoryService);

        // 2. Setup the Flash Sale Inventory
        int initialStock = 100;
        String productId = inventoryService.registerProduct("iPhone 15 Pro", initialStock);
        System.out.println("--- SYSTEM BOOT ---");
        System.out.println("Product Registered: iPhone 15 Pro | ID: " + productId + " | Initial Stock: " + initialStock);

        // 3. Register 1,000 Users for the Sale
        int totalUsers = 1000;
        List<String> userIds = new ArrayList<>();
        for (int i = 0; i < totalUsers; i++) {
            String userId = "USER_" + i;
            userIds.add(userId);
            flashSaleService.registerFlashSale(userId, productId);
        }
        System.out.println(totalUsers + " users successfully registered for the Flash Sale.\n");

        // 4. Setup Concurrency Tools
        ExecutorService executor = Executors.newFixedThreadPool(200); // 200 concurrent threads
        CountDownLatch startingGun = new CountDownLatch(1);
        CountDownLatch finishLine = new CountDownLatch(totalUsers);

        // Thread-safe collections to track results
        ConcurrentLinkedQueue<Order> successfulOrders = new ConcurrentLinkedQueue<>();
        AtomicInteger failedOrders = new AtomicInteger(0);

        System.out.println("--- PHASE 1: THE FLASH SALE RUSH ---");
        System.out.println("Simulating " + totalUsers + " users clicking 'Buy' at the exact same millisecond...");

        // 5. Build the concurrent purchase tasks
        for (String userId : userIds) {
            executor.submit(() -> {
                try {
                    startingGun.await(); // Wait for the green light

                    try {
                        Order order = flashSaleService.placeOrder(userId, productId);
                        successfulOrders.add(order);
                    } catch (RuntimeException e) {
                        // Expected to happen 900 times when stock runs out
                        failedOrders.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishLine.countDown();
                }
            });
        }

        // 6. FIRE THE STARTING GUN!
        long startTime = System.currentTimeMillis();
        startingGun.countDown();

        // Wait for all 1,000 requests to finish processing
        finishLine.await(10, TimeUnit.SECONDS);
        System.out.println("Flash Sale Rush processed in " + (System.currentTimeMillis() - startTime) + "ms.\n");

        // 7. Verify Atomicity & Race Conditions
        Product productAfterRush = inventoryService.getProduct(productId);
        System.out.println("--- PHASE 1 RESULTS ---");
        System.out.println("Successful Reservations : " + successfulOrders.size() + " (Expected: 100)");
        System.out.println("Failed Requests         : " + failedOrders.get() + " (Expected: 900)");
        System.out.println("Stock Remaining         : " + productAfterRush.getStock().get() + " (Expected: 0)");

        if (successfulOrders.size() == 100 && productAfterRush.getStock().get() == 0) {
            System.out.println("✅ ATOMICITY PASSED: Exactly 100 orders placed, no overselling, zero race conditions!\n");
        } else {
            System.err.println("❌ ATOMICITY FAILED: Stock or Order count mismatch!");
            System.exit(1);
        }

        // 8. Phase 2: Payment and Expiration Setup
        System.out.println("--- PHASE 2: PAYMENT & ASYNC EXPIRATION ---");
        List<Order> ordersList = new ArrayList<>(successfulOrders);

        // Confirm payment for the first 50 users instantly
        for (int i = 0; i < 50; i++) {
            flashSaleService.confirmPayment(ordersList.get(i).getId());
        }
        System.out.println("Paid for 50 orders instantly. Leaving the other 50 unpaid.");

        // 9. The TTL Waiting Game
        System.out.println("Waiting 32 seconds for the background DelayQueue to expire unpaid orders...");
        for(int i = 1; i <= 32; i++) {
            Thread.sleep(1000);
            if(i % 10 == 0) System.out.println(i + " seconds passed...");
        }

        // 10. Verify Expiration and Inventory Rollback
        System.out.println("\n--- PHASE 2 RESULTS ---");
        int confirmedCount = 0;
        int expiredCount = 0;

        for (Order order : ordersList) {
            if (order.getStatus().get()==OrderStatusEnum.CONFIRMED) confirmedCount++;
            if (order.getStatus().get() == OrderStatusEnum.EXPIRED) expiredCount++;
        }

        Product finalProduct = inventoryService.getProduct(productId);

        System.out.println("Confirmed Orders : " + confirmedCount + " (Expected: 50)");
        System.out.println("Expired Orders   : " + expiredCount + " (Expected: 50)");
        System.out.println("Final Stock      : " + finalProduct.getStock().get() + " (Expected: 50)");

        if (confirmedCount == 50 && expiredCount == 50 && finalProduct.getStock().get() == 50) {
            System.out.println("✅ EXPIRATION PASSED: Background thread perfectly expired unpaid orders and restored inventory without blocking!");
        } else {
            System.err.println("❌ EXPIRATION FAILED: State machine or inventory rollback failed.");
        }

        // Shutdown the executor so the JVM can exit
        executor.shutdown();
        System.exit(0);
    }
}
