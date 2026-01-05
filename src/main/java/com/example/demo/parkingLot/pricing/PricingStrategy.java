package com.example.demo.parkingLot.pricing;

import com.example.demo.parkingLot.Ticket;

public class PricingStrategy {
    public long price(Ticket ticket){
        return ticket.getParkingSpot().getPrice();
    }
}
