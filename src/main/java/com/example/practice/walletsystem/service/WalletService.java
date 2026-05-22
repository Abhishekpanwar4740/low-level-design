package com.example.practice.walletsystem.service;

import com.example.practice.walletsystem.exception.InsufficientFundsException;
import com.example.practice.walletsystem.exception.WalletNotFoundException;

import java.math.BigDecimal;


public interface WalletService { ;
    void createWallet(String userId);

    void addMoneyToWallet(String walletId, String currency, BigDecimal amount) throws WalletNotFoundException;

    void transferMoney(String fromWalletId, String toWalletId, String currency, BigDecimal amount) throws InsufficientFundsException, WalletNotFoundException;

    void withdrawMoneyFromWallet(String walletId, String currency, BigDecimal amount) throws InsufficientFundsException, WalletNotFoundException;

    void multiCurrencyTransfer(String fromWalletId,String toWalletId, String fromCurrency, String toCurrency, BigDecimal amount) throws InsufficientFundsException, WalletNotFoundException;
}
