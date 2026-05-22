package com.example.practice.flashsale.service;

import com.example.practice.flashsale.dto.Order;

public interface FlashSale {
    String registerFlashSale(String userId, String productId);
    Order placeOrder(String userId, String productId);
    Order confirmPayment(String orderId);
    Order makeOrderExpired(String orderId) throws IllegalArgumentException;
}
