package com.example.practice.flashsale.dto;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

@Data
public class Order {
    private String id;
    private String userId;
    private String productId;
    private AtomicReference<OrderStatusEnum> status;
    private Long createdAt;

    public Order(String id, String userId, String productId, AtomicReference<OrderStatusEnum> status) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.status = status;
        this.createdAt=System.currentTimeMillis();
    }

    public boolean updateStatus(OrderStatusEnum expected, OrderStatusEnum newStatus) {
        return this.status.compareAndSet(expected, newStatus);
    }
}
