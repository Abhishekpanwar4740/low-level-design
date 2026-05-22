package com.example.practice.flashsale.service;

import com.example.practice.flashsale.dto.Product;

public interface InventoryService {
    String registerProduct(String name, int quantity);
    void addProduct(String productId, int quantity) throws IllegalArgumentException;
    Product getProduct(String productId) throws IllegalArgumentException;
}
