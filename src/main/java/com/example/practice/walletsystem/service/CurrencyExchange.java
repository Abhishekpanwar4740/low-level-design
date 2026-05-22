package com.example.practice.walletsystem.service;

import com.example.practice.walletsystem.exception.InvalidCurrencyPairException;

import java.math.BigDecimal;

public interface CurrencyExchange {
    BigDecimal getCurrencyExchangeRate(String fromCurrency, String toCurrency) throws InvalidCurrencyPairException;
    void updateCurrencyExchangeRate(String fromCurrency, String toCurrency, BigDecimal newRate);
    void addCurrencyExchangeRate(String fromCurrency, String toCurrency, BigDecimal rate);
}
