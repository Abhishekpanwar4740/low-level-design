package com.example.practice.ticketbooking.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class Flight {
        private String flightNumber;
        private String departure;
        private String arrival;
        private Timestamp departureTime;
        private Timestamp arrivalTime;
        private int totalSeats;
        private List<Seat> allSeats;
}
