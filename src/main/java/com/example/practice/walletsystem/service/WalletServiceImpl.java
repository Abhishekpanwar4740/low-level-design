package com.example.practice.walletsystem.service;

import com.example.practice.walletsystem.dto.Transaction;
import com.example.practice.walletsystem.dto.TransactionStatusEnum;
import com.example.practice.walletsystem.dto.TransactionTypeEnum;
import com.example.practice.walletsystem.dto.Wallet;
import com.example.practice.walletsystem.exception.InsufficientFundsException;
import com.example.practice.walletsystem.exception.InvalidCurrencyPairException;
import com.example.practice.walletsystem.exception.UnsuccessfulCurrencyTransferException;
import com.example.practice.walletsystem.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WalletServiceImpl implements WalletService {
    Map<String, Wallet> walletMap;
    CurrencyExchange currencyExchange;

    public WalletServiceImpl(CurrencyExchange currencyExchange) {
        this.currencyExchange = currencyExchange;
        this.walletMap = new ConcurrentHashMap<>();
    }

    @Override
    public void createWallet(String userId) {
        Wallet wallet = new Wallet(UUID.randomUUID().toString(), userId);
        walletMap.put(wallet.getId(), wallet);
    }

    @Override
    public void addMoneyToWallet(String walletId, String currency, BigDecimal amount) throws WalletNotFoundException {
        if (!walletMap.containsKey(walletId))
            throw new WalletNotFoundException("Wallet with id " + walletId + " not found");
        Wallet wallet = walletMap.get(walletId);
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .fromWalletId(null)
                .toWalletId(walletId)
                .timestamp(System.currentTimeMillis())
                .status(TransactionStatusEnum.INPROGRESS)
                .type(TransactionTypeEnum.TOP_UP)
                .amount(amount)
                .currency(currency)
                .build();
        wallet.addTransactionToWallet(transaction);
        synchronized (wallet) {
            wallet.getCurrencyBalance().put(currency, wallet.getCurrencyBalance().getOrDefault(currency, BigDecimal.ZERO).add(amount));
        }
        transaction.setStatus(TransactionStatusEnum.SUCCESS);
    }

    @Override
    public void transferMoney(String fromWalletId, String toWalletId, String currency, BigDecimal amount) throws InsufficientFundsException, WalletNotFoundException {
        if (fromWalletId.equals(toWalletId)) {
            throw new IllegalArgumentException("Cannot transfer money to the same wallet.");
        }
        Wallet fromWallet = getWallet(fromWalletId);
        Wallet toWallet = getWallet(toWalletId);
        Wallet firstLock = fromWalletId.compareTo(toWalletId) < 0 ? fromWallet : toWallet;
        Wallet secondLock = fromWalletId.compareTo(toWalletId) < 0 ? toWallet : fromWallet;
        Transaction senderTransaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .fromWalletId(fromWalletId)
                .toWalletId(toWalletId)
                .timestamp(System.currentTimeMillis())
                .status(TransactionStatusEnum.INPROGRESS)
                .type(TransactionTypeEnum.SEND)
                .amount(amount)
                .currency(currency)
                .build();
        fromWallet.addTransactionToWallet(senderTransaction);
        synchronized (firstLock) {
            synchronized (secondLock) {
                if (fromWallet.getCurrencyBalance().getOrDefault(currency, BigDecimal.ZERO).compareTo(amount) < 0) {
                    senderTransaction.setStatus(TransactionStatusEnum.FAILED);
                    throw new InsufficientFundsException("Insufficient funds in wallet with id " + fromWalletId);
                }
                fromWallet.getCurrencyBalance().put(currency, fromWallet.getCurrencyBalance().get(currency).subtract(amount));
                toWallet.getCurrencyBalance().put(currency, toWallet.getCurrencyBalance().getOrDefault(currency, BigDecimal.ZERO).add(amount));
            }
        }
        senderTransaction.setStatus(TransactionStatusEnum.SUCCESS);
        Transaction receiverTransaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .fromWalletId(fromWalletId)
                .toWalletId(toWalletId)
                .timestamp(System.currentTimeMillis())
                .status(TransactionStatusEnum.SUCCESS)
                .type(TransactionTypeEnum.RECEIVED)
                .amount(amount)
                .currency(currency)
                .build();
        toWallet.addTransactionToWallet(receiverTransaction);
    }

    private Wallet getWallet(String walletId) {
        if (!walletMap.containsKey(walletId))
            throw new WalletNotFoundException("Wallet with id " + walletId + " not found");
        return walletMap.get(walletId);
    }

    @Override
    public void withdrawMoneyFromWallet(String walletId, String currency, BigDecimal amount) throws InsufficientFundsException, WalletNotFoundException {
        if (!walletMap.containsKey(walletId))
            throw new WalletNotFoundException("Wallet with id " + walletId + " not found");
        Wallet wallet = walletMap.get(walletId);
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .fromWalletId(walletId)
                .toWalletId(null)
                .timestamp(System.currentTimeMillis())
                .status(TransactionStatusEnum.INPROGRESS)
                .type(TransactionTypeEnum.WITHDRAW)
                .amount(amount)
                .currency(currency)
                .build();
        wallet.addTransactionToWallet(transaction);
        synchronized (wallet) {
            if (wallet.getCurrencyBalance().getOrDefault(currency, BigDecimal.ZERO).compareTo(amount) < 0) {
                transaction.setStatus(TransactionStatusEnum.FAILED);
                throw new InsufficientFundsException("Insufficient funds in wallet with id " + walletId);
            }
            wallet.getCurrencyBalance().put(currency, wallet.getCurrencyBalance().getOrDefault(currency, BigDecimal.ZERO).subtract(amount));
        }
        transaction.setStatus(TransactionStatusEnum.SUCCESS);
    }

    @Override
    public void multiCurrencyTransfer(String fromWalletId, String toWalletId, String fromCurrency, String toCurrency, BigDecimal amount) throws IllegalArgumentException,InsufficientFundsException, WalletNotFoundException {
        if (fromWalletId.equals(toWalletId)) {
            throw new IllegalArgumentException("Cannot transfer money to the same wallet.");
        }
        try {
            BigDecimal forwardRate = currencyExchange.getCurrencyExchangeRate(fromCurrency, toCurrency);
            BigDecimal amountToBeAdded = amount.multiply(forwardRate);
            Wallet fromWallet = getWallet(fromWalletId);
            Wallet toWallet = getWallet(toWalletId);
            Transaction senderTransaction = Transaction.builder()
                    .id(UUID.randomUUID().toString())
                    .fromWalletId(fromWalletId)
                    .toWalletId(toWalletId)
                    .timestamp(System.currentTimeMillis())
                    .status(TransactionStatusEnum.INPROGRESS)
                    .type(TransactionTypeEnum.SEND)
                    .amount(amount)
                    .currency(fromCurrency)
                    .build();
            fromWallet.addTransactionToWallet(senderTransaction);
            Wallet firstLock = fromWalletId.compareTo(toWalletId) < 0 ? fromWallet : toWallet;
            Wallet secondLock = fromWalletId.compareTo(toWalletId) < 0 ? toWallet : fromWallet;
            synchronized (firstLock) {
                synchronized (secondLock) {
                    if (fromWallet.getCurrencyBalance().getOrDefault(fromCurrency, BigDecimal.ZERO).compareTo(amount) < 0) {
                        senderTransaction.setStatus(TransactionStatusEnum.FAILED);
                        throw new InsufficientFundsException("Insufficient funds in sender's wallet with id " + fromWalletId);
                    }
                    fromWallet.getCurrencyBalance().put(fromCurrency, fromWallet.getCurrencyBalance().get(fromCurrency).subtract(amount));
                    toWallet.getCurrencyBalance().put(toCurrency, toWallet.getCurrencyBalance().getOrDefault(toCurrency, BigDecimal.ZERO).add(amountToBeAdded));
                }
            }
            senderTransaction.setStatus(TransactionStatusEnum.SUCCESS);
            Transaction receiverTransaction = Transaction.builder()
                    .id(UUID.randomUUID().toString())
                    .fromWalletId(fromWalletId)
                    .toWalletId(toWalletId)
                    .timestamp(System.currentTimeMillis())
                    .status(TransactionStatusEnum.SUCCESS)
                    .type(TransactionTypeEnum.RECEIVED)
                    .amount(amount)
                    .currency(toCurrency)
                    .build();
            toWallet.addTransactionToWallet(receiverTransaction);

        } catch (InvalidCurrencyPairException e) {
            throw new UnsuccessfulCurrencyTransferException("Error in currency exchange for fromCurrency " + fromCurrency + " and toCurrency " + toCurrency + ". Error message:) " + e.getMessage());
        }
    }

}
