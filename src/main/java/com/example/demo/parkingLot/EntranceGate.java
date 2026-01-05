package com.example.demo.parkingLot;

import com.example.demo.parkingLot.parkingStrategy.ParkingStrategy;
import com.example.demo.parkingLot.parkingspot.ParkingManagerFactory;
import com.example.demo.parkingLot.parkingspot.ParkingSpot;
import com.example.demo.parkingLot.parkingspot.ParkingSpotManager;
import com.example.demo.parkingLot.pricing.PricingStrategy;


public class EntranceGate {
    ParkingManagerFactory parkingManagerFactory;
    ParkingSpotManager parkingSpotManager;

    public EntranceGate(ParkingManagerFactory parkingManagerFactory) {
        this.parkingManagerFactory = parkingManagerFactory;
    }

    public ParkingSpot findSpace(String vehicleType, ParkingStrategy parkingStrategy) {
        parkingSpotManager = parkingManagerFactory.getParkingManager(vehicleType);
        ParkingSpot parkingSpot = parkingSpotManager.findParkingSpace(vehicleType, parkingStrategy);
        return parkingSpot;
    }

    public void bookSpot(Vehicle vehicle, ParkingSpot parkingSpot) {
        parkingSpotManager = parkingManagerFactory.getParkingManager(vehicle.vehicleType);
        parkingSpotManager.parkVehicle(vehicle, parkingSpot);
    }

    public Ticket getTicket(Vehicle vehicle, PricingStrategy pricingStrategy,ParkingStrategy parkingStrategy) {
        ParkingSpot parkingSpot=findSpace(vehicle.getVehicleType(),parkingStrategy);
        bookSpot(vehicle,parkingSpot);
        Ticket ticket=new Ticket(vehicle,parkingSpot,pricingStrategy);
        vehicle.setTicket(ticket);
        return ticket;
    }
}
