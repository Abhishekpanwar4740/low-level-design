package com.example.practice.walletsystem.dto;

import lombok.Data;

@Data
public class Transanction {
    private String id;
    private String fromWalletId;
    private String toWalletId;
    private String currency;
    private Long amount;
    private Long timestamp;
    private TransanctionStatusEnum status;
}
