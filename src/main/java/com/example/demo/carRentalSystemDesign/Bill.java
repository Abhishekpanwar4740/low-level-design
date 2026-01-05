package com.example.demo.carRentalSystemDesign;

public class Bill {
    Reservation reservation;
    boolean isPaid;
    double amount;

    public Bill(Reservation reservation) {
        this.reservation = reservation;
        this.isPaid = false;
        this.amount = computeBillAmount();
    }

    private double computeBillAmount() {
        return 100.0;
    }

}
