package com.example.practice.flashsale.service;

import com.example.practice.flashsale.dto.DelayedOrder;
import com.example.practice.flashsale.dto.Order;
import com.example.practice.flashsale.dto.OrderStatusEnum;
import com.example.practice.flashsale.exception.OutOfStockException;
import com.example.practice.flashsale.exception.UserAlreadyRegisteredException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class FlashSaleImpl implements FlashSale {
    private Map<String, Set<String>> userRegistrations; // userId -> list of productIds
    private Map<String, Order> orders; // orderId -> Order
    private final InventoryService inventoryService;
    private DelayQueue<DelayedOrder> expiryQueue;
    private ExecutorService service;

    public FlashSaleImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.userRegistrations = new ConcurrentHashMap<>();
        this.orders = new ConcurrentHashMap<>();
        this.expiryQueue = new DelayQueue<>();
        this.service = Executors.newSingleThreadExecutor();
        startExpiryWorker();
    }
    private void startExpiryWorker(){
        service.submit(()->{
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DelayedOrder delayedOrder = expiryQueue.take();
                    makeOrderExpired(delayedOrder.getOrder().getId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Override
    public String registerFlashSale(String userId, String productId) throws UserAlreadyRegisteredException {
        if (userRegistrations.getOrDefault(productId, new CopyOnWriteArraySet<>()).contains(userId)) {
            throw new UserAlreadyRegisteredException(String.format("User already registered for product %s flash sale", productId));
        }
        Set<String> users = userRegistrations.getOrDefault(productId, new CopyOnWriteArraySet<>());
        users.add(userId);
        userRegistrations.put(productId, users);
        return String.format("User %s registered for product %s flash sale", userId, productId);
    }

    @Override
    public Order placeOrder(String userId, String productId) throws OutOfStockException {
        int currStock;
        do {
            currStock = inventoryService.getProduct(productId).getStock().get();
            if (currStock <= 0) {
                throw new OutOfStockException(String.format("Product %s is out of stock", productId));
            }
        } while (!inventoryService.getProduct(productId).getStock().compareAndSet(currStock, currStock - 1));
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, userId, productId, new AtomicReference<>(OrderStatusEnum.RESERVED));
        orders.put(orderId, order);
        expiryQueue.add(new DelayedOrder(order, 30000)); // Order expires after 30 seconds
        return order;
    }

    @Override
    public Order confirmPayment(String orderId) throws IllegalArgumentException, IllegalStateException {
        if (!orders.containsKey(orderId)) {
            throw new IllegalArgumentException("Order not found");
        }
        Order order = orders.get(orderId);
        if (order.updateStatus(OrderStatusEnum.RESERVED, OrderStatusEnum.CONFIRMED)) {
            return order;
        } else
            throw new IllegalStateException("Order is not in RESERVED state. Current state: " + order.getStatus());
    }

    @Override
    public Order makeOrderExpired(String orderId) throws IllegalArgumentException {
        if (!orders.containsKey(orderId)) {
            throw new IllegalArgumentException("Order not found");
        }
        Order order = orders.get(orderId);
        if (order.updateStatus(OrderStatusEnum.RESERVED, OrderStatusEnum.EXPIRED)) {
            System.out.println("Order " + orderId + " EXPIRED. Returning inventory.");
            inventoryService.addProduct(order.getProductId(), 1);
        }
        return order;
    }
}
