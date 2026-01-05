package com.example.demo.parkingLot.pricing;

import com.example.demo.parkingLot.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class MinutePricingStrategy extends PricingStrategy {
    @Override
    public long price(Ticket ticket) {
        long numberOfMinutes = calculateMinutes(ticket.getEntryTime());
        return ticket.getParkingSpot().getPrice() * numberOfMinutes;
    }

    public long calculateMinutes(LocalDateTime entryTime) {
        LocalDateTime currTime = LocalDateTime.now();
        return Duration.between(entryTime,currTime).toMinutes();
    }
}
