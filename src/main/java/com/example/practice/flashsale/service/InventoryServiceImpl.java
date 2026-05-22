package com.example.practice.flashsale.service;

import com.example.practice.flashsale.dto.Product;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryServiceImpl implements InventoryService {
    Map<String, Product> productMap;

    public InventoryServiceImpl() {
        this.productMap = new ConcurrentHashMap<>();
    }

    @Override
    public String registerProduct(String name, int quantity) {
        Product product = new Product(UUID.randomUUID().toString(), name, new java.util.concurrent.atomic.AtomicInteger(quantity));
        productMap.put(product.getId(), product);
        return product.getId();
    }

    @Override
    public void addProduct(String productId, int quantity) throws IllegalArgumentException {
        if (!productMap.containsKey(productId)) {
            throw new IllegalArgumentException("Product not found");
        }
        int updatedStock = productMap.get(productId).getStock().addAndGet(quantity);
        System.out.println("Added " + quantity + " units to product " + productId);
        System.out.println("Current stock for product " + productId + ": " + updatedStock);
    }

    @Override
    public Product getProduct(String productId) throws IllegalArgumentException {
        if (!productMap.containsKey(productId)) {
            throw new IllegalArgumentException("Product not found");
        }
        return productMap.get(productId);
    }
}
