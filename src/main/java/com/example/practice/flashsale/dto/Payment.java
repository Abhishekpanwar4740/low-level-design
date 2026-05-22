package com.example.practice.flashsale.dto;

import lombok.Data;

@Data
public class Payment {
    private String orderId;
    private String userId;
    private String productId;
    private double amount;
    private PaymentStatusEnum status;
}
