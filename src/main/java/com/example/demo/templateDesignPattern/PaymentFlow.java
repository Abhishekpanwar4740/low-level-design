package com.example.demo.templateDesignPattern;

public abstract class PaymentFlow {
    public abstract void validateRequest();

    public abstract void calculateFees();

    public abstract void debitMoney();

    public abstract void creditMoney();

    public final void sendMoney() {
        validateRequest();
        debitMoney();
        calculateFees();
        creditMoney();
    }
}
