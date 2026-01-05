package com.example.demo.parkingLot;

import com.example.demo.parkingLot.parkingspot.ParkingSpot;
import com.example.demo.parkingLot.pricing.PricingStrategy;

import java.time.LocalDateTime;

public class Ticket {
    Vehicle vehicle;
    ParkingSpot parkingSpot;
    LocalDateTime entryTime;
    PricingStrategy pricingStrategy;

    public Ticket(Vehicle vehicle, ParkingSpot parkingSpot, PricingStrategy pricingStrategy) {
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
        this.pricingStrategy = pricingStrategy;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

}
