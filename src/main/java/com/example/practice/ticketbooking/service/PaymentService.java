package com.example.practice.ticketbooking.service;

public class PaymentService {
        public boolean processPayment(String bookingId, double amount) {
            // Simulate payment processing logic
            // In a real application, this would involve integrating with a payment gateway
            int randomNum = (int)(Math.random() * 10);
            if(randomNum < 2) {
                System.out.println("Payment failed for booking ID: " + bookingId);
                return false;
            }
            System.out.println("Processing payment for booking ID: " + bookingId + " with amount: " + amount);
            return true; // Simulate successful payment
        }
}
