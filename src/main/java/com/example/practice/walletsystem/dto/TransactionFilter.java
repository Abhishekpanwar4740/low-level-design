package com.example.practice.walletsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionFilter {
    private OrderEnum orderBy;
    private BigDecimal amountGreaterThan;
}
