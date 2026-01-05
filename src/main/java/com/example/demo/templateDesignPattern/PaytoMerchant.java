package com.example.demo.templateDesignPattern;

public class PaytoMerchant extends PaymentFlow{
    @Override
    public void validateRequest() {
        System.out.println("Validate PaytoMerchant");
    }

    @Override
    public void calculateFees() {
        System.out.println("calculate fees PaytoMerchant");

    }

    @Override
    public void debitMoney() {
        System.out.println("debit PaytoMerchant");

    }

    @Override
    public void creditMoney() {
        System.out.println("credit PaytoMerchant");

    }
}
