package com.example.practice.ticketbooking.dto;

import lombok.Data;

import java.util.List;
@Data
public class Booking {
    private String bookingId;
    private String userId;
    private String flightId;
    private Double amount;
    private List<Seat> seats;
    private BookingStatus status;
}
