package com.example.practice.walletsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Transaction {
    private String id;
    private String fromWalletId;
    private String toWalletId;
    private TransactionTypeEnum type;
    private String currency;
    private BigDecimal amount;
    private Long timestamp;
    private TransactionStatusEnum status;
}
