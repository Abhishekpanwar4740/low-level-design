package com.example.practice.flashsale.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }
    public OutOfStockException(String message,String productId) {
        super(message + " Product ID: " + productId);
    }
}
