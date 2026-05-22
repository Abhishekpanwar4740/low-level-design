package com.example.practice.ticketbooking.dto;

import lombok.Data;

import java.util.concurrent.locks.ReentrantLock;

@Data
public class Seat {
        private String seatId;
        private String flightId;
        private String seatNumber;
        private SeatStatus status;
        private Double price;
        private final ReentrantLock lock = new ReentrantLock();
}
