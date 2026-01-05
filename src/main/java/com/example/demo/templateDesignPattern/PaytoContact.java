package com.example.demo.templateDesignPattern;

public class PaytoContact extends PaymentFlow{

    @Override
    public void validateRequest() {
        System.out.println("Validate PaytoContact");
    }

    @Override
    public void calculateFees() {
        System.out.println("calculate fees PaytoContact");

    }

    @Override
    public void debitMoney() {
        System.out.println("debit PaytoContact");

    }

    @Override
    public void creditMoney() {
        System.out.println("credit PaytoContact");

    }
}
