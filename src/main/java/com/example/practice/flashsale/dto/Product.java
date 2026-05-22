package com.example.practice.flashsale.dto;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Product {
    private String id;
    private String name;
    private AtomicInteger stock;

    public Product(String id, String name, AtomicInteger stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }
}
