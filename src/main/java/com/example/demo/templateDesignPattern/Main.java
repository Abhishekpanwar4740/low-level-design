package com.example.demo.templateDesignPattern;

public class Main {
    public static void main(String[] args) {
        PaymentFlow paytoContact = new PaytoContact();
        PaymentFlow paytoMerchant = new PaytoMerchant();
        paytoMerchant.sendMoney();
        paytoContact.sendMoney();
    }
}
