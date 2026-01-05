package com.example.demo.parkingLot.pricing;

import com.example.demo.parkingLot.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyPricingStrategy extends PricingStrategy{
    @Override
    public long price(Ticket ticket){
        long numberOfHours=calculateHours(ticket.getEntryTime());
        return ticket.getParkingSpot().getPrice()*numberOfHours;
    }
    public long calculateHours(LocalDateTime entryTime){
        LocalDateTime currTime=LocalDateTime.now();
        return Duration.between(entryTime,currTime).toHours();
    }
}
