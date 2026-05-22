package com.example.practice.walletsystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Wallet {
    private String id;
    private String userId;
    private Map<String, BigDecimal> currencyBalance;
    private List<Transaction> transactions;

    public Wallet(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.currencyBalance=new ConcurrentHashMap<>();
        this.transactions=new CopyOnWriteArrayList<>();
    }
    public void addTransactionToWallet(Transaction transaction){
        transactions.add(transaction);
    }
    public List<Transaction> getTransactionsWithFilter(TransactionFilter filter){
        if(filter==null){
            return transactions;
        }
        List<Transaction> transactions=new ArrayList<>(this.transactions);
        if(filter.getOrderBy()!=null) {
            if (filter.getOrderBy() == OrderEnum.ASC) {
                transactions.sort(Comparator.comparingLong(Transaction::getTimestamp));
            } else {
                transactions.sort((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));
            }
        }
        if(filter.getAmountGreaterThan()!=null){
            transactions.removeIf(transaction -> transaction.getAmount().compareTo(filter.getAmountGreaterThan())<0);
        }
        return transactions;
    }
}
