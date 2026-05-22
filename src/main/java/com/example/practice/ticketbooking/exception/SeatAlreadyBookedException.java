package com.example.practice.ticketbooking.exception;

public class SeatAlreadyBookedException extends RuntimeException{
    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}
