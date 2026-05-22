package com.example.practice.walletsystem.service;

import com.example.practice.walletsystem.dto.Wallet;
import com.example.practice.walletsystem.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DigitalWalletApplication {
//    case-1 test for atomicity and thread safety of transferMoney method under high concurrency
//    public static void main(String[] args) throws InterruptedException {
//        // 1. Initialize Services
//        CurrencyExchange currencyExchange = new CurrencyExchangeServiceImpl();
//        WalletServiceImpl walletService = new WalletServiceImpl(currencyExchange);
//
//        // 2. Create 5 Wallets
//        int numWallets = 5;
//        BigDecimal initialBalance = new BigDecimal("1000.00");
//        String currency = "USD";
//
//        for (int i = 0; i < numWallets; i++) {
//            walletService.createWallet("USER_" + i);
//        }
//
//        // Grab the generated wallet IDs directly from the map (package-private access)
//        List<String> walletIds = new ArrayList<>(walletService.walletMap.keySet());
//
//        // 3. Top-up each wallet with $1000
//        for (String id : walletIds) {
//            walletService.addMoneyToWallet(id, currency, initialBalance);
//        }
//
//        BigDecimal expectedTotalSystemMoney = initialBalance.multiply(new BigDecimal(numWallets));
//        System.out.println("--- Starting Stress Test ---");
//        System.out.println("Total System Money Expected at the end: " + currency + " " + expectedTotalSystemMoney);
//        System.out.println("----------------------------\n");
//
//        // 4. Setup Concurrency (50 threads doing 100 transfers each = 5,000 total transfers)
//        int numThreads = 50;
//        int transfersPerThread = 100;
//
//        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
//        CountDownLatch startingGun = new CountDownLatch(1);
//        CountDownLatch finishLine = new CountDownLatch(numThreads);
//        Random random = new Random();
//
//        for (int i = 0; i < numThreads; i++) {
//            executor.submit(() -> {
//                try {
//                    startingGun.await(); // Wait for the green light so all threads hit the service at once
//
//                    for (int j = 0; j < transfersPerThread; j++) {
//                        // Pick random distinct sender and receiver
//                        String fromWallet = walletIds.get(random.nextInt(numWallets));
//                        String toWallet = walletIds.get(random.nextInt(numWallets));
//
//                        while (fromWallet.equals(toWallet)) {
//                            toWallet = walletIds.get(random.nextInt(numWallets));
//                        }
//
//                        // Transfer a random amount between $1 and $50
//                        BigDecimal amount = new BigDecimal(random.nextInt(50) + 1);
//
//                        try {
//                            walletService.transferMoney(fromWallet, toWallet, currency, amount);
//                        } catch (InsufficientFundsException e) {
//                            // This is completely expected in a random chaotic test.
//                            // We ignore it and continue trying other transfers.
//                        } catch (Exception e) {
//                            System.err.println("Unexpected Error: " + e.getMessage());
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                } finally {
//                    finishLine.countDown();
//                }
//            });
//        }
//
//        // 5. Fire the threads!
//        System.out.println("Firing " + (numThreads * transfersPerThread) + " concurrent transfers...");
//        long startTime = System.currentTimeMillis();
//        startingGun.countDown();
//
//        // 6. Wait for all threads to complete
//        boolean completedWithoutDeadlock = finishLine.await(30, TimeUnit.SECONDS);
//        executor.shutdown();
//
//        if (!completedWithoutDeadlock) {
//            System.err.println("\n❌ TEST FAILED: System deadlocked! Threads did not finish in time.");
//            System.exit(1);
//        }
//        System.out.println("All transfers completed in " + (System.currentTimeMillis() - startTime) + "ms.\n");
//
//        // 7. Verify Atomicity
//        System.out.println("--- Final Wallet Balances ---");
//        BigDecimal actualTotalSystemMoney = BigDecimal.ZERO;
//
//        for (String id : walletIds) {
//            Wallet wallet = walletService.walletMap.get(id);
//            BigDecimal balance = wallet.getCurrencyBalance().get(currency);
//            actualTotalSystemMoney = actualTotalSystemMoney.add(balance);
//
//            System.out.println("Wallet [" + id.substring(0,8) + "...] : " + currency + " " + balance);
//        }
//
//        System.out.println("\n----------------------------");
//        System.out.println("Expected Total: " + expectedTotalSystemMoney);
//        System.out.println("Actual Total  : " + actualTotalSystemMoney);
//
//        if (expectedTotalSystemMoney.compareTo(actualTotalSystemMoney) == 0) {
//            System.out.println("✅ ATOMICITY PASSED: Not a single cent was lost or created out of thin air!");
//        } else {
//            System.err.println("❌ ATOMICITY FAILED: Data inconsistency detected.");
//        }
//    }
//    case-2 multicurrency transfer test with 2 threads transferring between the same 2 wallets at the same time to check
public static void main(String[] args) throws InterruptedException {
    // 1. Initialize Services & Rates
    CurrencyExchange currencyExchange = new CurrencyExchangeServiceImpl();

    // Setup Rates: 1 USD = 80 INR
    BigDecimal usdToInrRate = new BigDecimal("80.0");
    currencyExchange.addCurrencyExchangeRate("USD", "INR", usdToInrRate);

    // We need the reverse rate too for total chaos: 1 INR = 0.0125 USD (which is exactly 1/80)
    BigDecimal inrToUsdRate = new BigDecimal("0.0125");
    currencyExchange.addCurrencyExchangeRate("INR", "USD", inrToUsdRate);

    WalletServiceImpl walletService = new WalletServiceImpl(currencyExchange);

    // 2. Create 5 Wallets
    int numWallets = 5;
    BigDecimal initialBalanceUsd = new BigDecimal("1000.00");

    for (int i = 0; i < numWallets; i++) {
        walletService.createWallet("USER_" + i);
    }

    List<String> walletIds = new ArrayList<>(walletService.walletMap.keySet());

    // 3. Top-up each wallet with $1000 USD and 0 INR
    for (String id : walletIds) {
        walletService.addMoneyToWallet(id, "USD", initialBalanceUsd);
        // Initialize INR to 0 to avoid NullPointerExceptions later
        walletService.walletMap.get(id).getCurrencyBalance().put("INR", BigDecimal.ZERO);
    }

    BigDecimal expectedSystemValueUsd = initialBalanceUsd.multiply(new BigDecimal(numWallets));
    System.out.println("--- Starting Multi-Currency Stress Test ---");
    System.out.println("Total System Value Expected: USD " + expectedSystemValueUsd);
    System.out.println("-------------------------------------------\n");

    // 4. Setup Concurrency (50 threads doing 100 transfers each)
    int numThreads = 50;
    int transfersPerThread = 100;

    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    CountDownLatch startingGun = new CountDownLatch(1);
    CountDownLatch finishLine = new CountDownLatch(numThreads);
    Random random = new Random();

    for (int i = 0; i < numThreads; i++) {
        executor.submit(() -> {
            try {
                startingGun.await();

                for (int j = 0; j < transfersPerThread; j++) {
                    String fromWallet = walletIds.get(random.nextInt(numWallets));
                    String toWallet = walletIds.get(random.nextInt(numWallets));

                    while (fromWallet.equals(toWallet)) {
                        toWallet = walletIds.get(random.nextInt(numWallets));
                    }

                    // Randomly decide direction: USD -> INR or INR -> USD
                    boolean isUsdToInr = random.nextBoolean();
                    String fromCurr = isUsdToInr ? "USD" : "INR";
                    String toCurr = isUsdToInr ? "INR" : "USD";

                    // Transfer random amounts (1 to 10 USD, or 80 to 800 INR)
                    BigDecimal amount = isUsdToInr
                            ? new BigDecimal(random.nextInt(10) + 1)
                            : new BigDecimal((random.nextInt(10) + 1) * 80);

                    try {
                        walletService.multiCurrencyTransfer(fromWallet, toWallet, fromCurr, toCurr, amount);
                    } catch (InsufficientFundsException e) {
                        // Ignored: Normal during chaotic random transfers
                    } catch (Exception e) {
                        System.err.println("Unexpected Error: " + e.getMessage());
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                finishLine.countDown();
            }
        });
    }

    // 5. Fire the threads!
    System.out.println("Firing " + (numThreads * transfersPerThread) + " concurrent cross-currency transfers...");
    long startTime = System.currentTimeMillis();
    startingGun.countDown();

    // 6. Wait for completion
    boolean completedWithoutDeadlock = finishLine.await(30, TimeUnit.SECONDS);
    executor.shutdown();

    if (!completedWithoutDeadlock) {
        System.err.println("\n❌ TEST FAILED: System deadlocked!");
        System.exit(1);
    }
    System.out.println("All transfers completed in " + (System.currentTimeMillis() - startTime) + "ms.\n");

    // 7. Verify Multi-Currency Atomicity
    System.out.println("--- Final Wallet Balances ---");
    BigDecimal totalUsdInSystem = BigDecimal.ZERO;
    BigDecimal totalInrInSystem = BigDecimal.ZERO;

    for (String id : walletIds) {
        Wallet wallet = walletService.walletMap.get(id);
        BigDecimal usd = wallet.getCurrencyBalance().getOrDefault("USD", BigDecimal.ZERO);
        BigDecimal inr = wallet.getCurrencyBalance().getOrDefault("INR", BigDecimal.ZERO);

        totalUsdInSystem = totalUsdInSystem.add(usd);
        totalInrInSystem = totalInrInSystem.add(inr);

        System.out.printf("Wallet [%s...] : %8.2f USD  |  %8.2f INR\n", id.substring(0,8), usd, inr);
    }

    // Convert total INR back to USD to check the system's global invariant
    // Using setScale to ensure we don't get tiny fractional floating-point artifacts
    BigDecimal inrConvertedToUsd = totalInrInSystem.divide(usdToInrRate, 2, RoundingMode.HALF_UP);
    BigDecimal actualSystemValueUsd = totalUsdInSystem.add(inrConvertedToUsd).setScale(2, RoundingMode.HALF_UP);

    System.out.println("\n-------------------------------------------");
    System.out.printf("Total Raw USD left    : %.2f\n", totalUsdInSystem);
    System.out.printf("Total Raw INR left    : %.2f\n", totalInrInSystem);
    System.out.printf("INR converted to USD  : %.2f\n", inrConvertedToUsd);
    System.out.println("-------------------------------------------");
    System.out.printf("Expected System Value : %.2f USD\n", expectedSystemValueUsd);
    System.out.printf("Actual System Value   : %.2f USD\n", actualSystemValueUsd);

    if (expectedSystemValueUsd.compareTo(actualSystemValueUsd) == 0) {
        System.out.println("\n✅ MULTI-CURRENCY ATOMICITY PASSED: Exchange rates applied correctly and no value was lost in the chaos!");
    } else {
        System.err.println("\n❌ ATOMICITY FAILED: Data inconsistency detected. Value leaked or was created.");
    }
}
}