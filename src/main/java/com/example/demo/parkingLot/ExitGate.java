package com.example.demo.parkingLot;

import com.example.demo.parkingLot.parkingspot.ParkingManagerFactory;
import com.example.demo.parkingLot.parkingspot.ParkingSpotManager;
import com.example.demo.parkingLot.pricing.CostComputation;
import com.example.demo.parkingLot.pricing.CostComputationFactory;

public class ExitGate {
    ParkingManagerFactory parkingManagerFactory;
    ParkingSpotManager parkingSpotManager;
    CostComputationFactory costComputationFactory;
    CostComputation costComputation;

    public ExitGate(ParkingManagerFactory parkingManagerFactory, CostComputationFactory costComputationFactory) {
        this.parkingManagerFactory = parkingManagerFactory;
        this.costComputationFactory = costComputationFactory;
    }

    public void exit(Vehicle vehicle) {
        long ticketPrice = this.price(vehicle.getTicket());
        System.out.println("Vehicle number " + vehicle.getVehicleNumber() + " has to pay : " + ticketPrice);
        freeSpot(vehicle.getTicket());
    }

    public long price(Ticket ticket) {
        costComputation = costComputationFactory.getCostComputation(ticket.getVehicle().vehicleType, ticket.getPricingStrategy());
        return costComputation.price(ticket);
    }

    public void freeSpot(Ticket ticket) {
        parkingSpotManager = parkingManagerFactory.getParkingManager(ticket.getVehicle().vehicleType);
        parkingSpotManager.removeVehicle(ticket.getParkingSpot());
        parkingSpotManager.priorityQueue.add(ticket.getParkingSpot());
    }
}
