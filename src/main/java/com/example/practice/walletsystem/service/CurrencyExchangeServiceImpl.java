package com.example.practice.walletsystem.service;

import com.example.practice.walletsystem.exception.InvalidCurrencyPairException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyExchangeServiceImpl implements CurrencyExchange {
    Map<String, Map<String, BigDecimal>> exchangeRates;

    public CurrencyExchangeServiceImpl() {
        this.exchangeRates = new ConcurrentHashMap<>();
    }

    @Override
    public BigDecimal getCurrencyExchangeRate(String fromCurrency, String toCurrency) throws InvalidCurrencyPairException {
        if (exchangeRates.containsKey(fromCurrency) && exchangeRates.get(fromCurrency).containsKey(toCurrency)) {
            return exchangeRates.get(fromCurrency).get(toCurrency);
        }
        throw new InvalidCurrencyPairException("Exchange rate for " + fromCurrency + " to " + toCurrency + " not found");
    }

    @Override
    public void updateCurrencyExchangeRate(String fromCurrency, String toCurrency, BigDecimal newRate) {
        addCurrencyExchangeRate(fromCurrency, toCurrency, newRate);
        System.out.println("Exchange rate for " + fromCurrency + " to " + toCurrency + " updated to " + newRate);
    }

    @Override
    public void addCurrencyExchangeRate(String fromCurrency, String toCurrency, BigDecimal rate) {
        exchangeRates.putIfAbsent(fromCurrency, new HashMap<>());
        exchangeRates.get(fromCurrency).put(toCurrency, rate);
    }

}
