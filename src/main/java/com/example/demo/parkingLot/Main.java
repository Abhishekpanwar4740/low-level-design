package com.example.demo.parkingLot;

import com.example.demo.parkingLot.parkingStrategy.NearToEntrancePS;
import com.example.demo.parkingLot.parkingspot.*;
import com.example.demo.parkingLot.pricing.*;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        ParkingManagerFactory parkingManagerFactory = new ParkingManagerFactory();
        CostComputationFactory costComputationFactory = new CostComputationFactory();
        ParkingSpotManager twoWheelerManager = new TwoWheelerParkingSpotManager();
        ParkingSpotManager fourWheelerManager = new FourWheelerParkingSpotManager();
        int count = 1;
        for (int i = 0; i < 500; i++) {
            ParkingSpot parkingSpot = new TwoWheelerSpot();
            parkingSpot.setId(count);
            parkingSpot.setEmpty(true);
            count++;
            twoWheelerManager.addParkingSpace(parkingSpot);
        }
        count = 1;
        for (int i = 0; i < 500; i++) {
            ParkingSpot parkingSpot = new FourWheelerSpot();
            parkingSpot.setId(count);
            parkingSpot.setEmpty(true);
            count++;
            fourWheelerManager.addParkingSpace(parkingSpot);
        }
        System.out.println("two wheeler spots added " + twoWheelerManager.getParkingSpotList().size());
        System.out.println("four wheeler spots added " + fourWheelerManager.getParkingSpotList().size());
        EntranceGate entranceGate1 = new EntranceGate(parkingManagerFactory);
        EntranceGate entranceGate2 = new EntranceGate(parkingManagerFactory);
        EntranceGate entranceGate3 = new EntranceGate(parkingManagerFactory);
        EntranceGate entranceGate4 = new EntranceGate(parkingManagerFactory);

        NearToEntrancePS nearToEntrancePS = new NearToEntrancePS();

        Vehicle vehicle1 = new Vehicle(1234, VehicleType.TWOWHEELER.getValue());
        Vehicle vehicle2 = new Vehicle(2345, VehicleType.FOURWHEELER.getValue());
        Vehicle vehicle3 = new Vehicle(1334, VehicleType.TWOWHEELER.getValue());
        Vehicle vehicle4 = new Vehicle(2445, VehicleType.FOURWHEELER.getValue());
        Vehicle vehicle5 = new Vehicle(1244, VehicleType.TWOWHEELER.getValue());
        Vehicle vehicle6 = new Vehicle(2355, VehicleType.FOURWHEELER.getValue());

        PricingStrategy pricingStrategy1 = new HourlyPricingStrategy();
        PricingStrategy pricingStrategy2 = new MinutePricingStrategy();

        entranceGate1.getTicket(vehicle1,pricingStrategy2,nearToEntrancePS);
        System.out.println("Parking with id " +vehicle1.ticket.getParkingSpot().getId()+" allocated to vehicle number "+ vehicle1.getVehicleNumber());
        entranceGate2.getTicket(vehicle2,pricingStrategy2,nearToEntrancePS);
        System.out.println("Parking with id " +vehicle2.ticket.getParkingSpot().getId()+" allocated to vehicle number "+ vehicle2.getVehicleNumber());
        entranceGate1.getTicket(vehicle3,pricingStrategy1,nearToEntrancePS);
        System.out.println("Parking with id " +vehicle3.ticket.getParkingSpot().getId()+" allocated to vehicle number "+ vehicle3.getVehicleNumber());
        entranceGate3.getTicket(vehicle4,pricingStrategy2,nearToEntrancePS);
        System.out.println("Parking with id " +vehicle4.ticket.getParkingSpot().getId()+" allocated to vehicle number "+ vehicle4.getVehicleNumber());
        entranceGate1.getTicket(vehicle5,pricingStrategy1,nearToEntrancePS);
        System.out.println("Parking with id " +vehicle5.ticket.getParkingSpot().getId()+" allocated to vehicle number "+ vehicle5.getVehicleNumber());
        entranceGate4.getTicket(vehicle6,pricingStrategy1,nearToEntrancePS);
        System.out.println("Parking with id " +vehicle6.ticket.getParkingSpot().getId()+" allocated to vehicle number "+ vehicle6.getVehicleNumber());

        System.out.println("Available two wheeler parking "+TwoWheelerParkingSpotManager.priorityQueue.size());
        System.out.println("Available four wheeler parking "+FourWheelerParkingSpotManager.priorityQueue.size());

        ExitGate exitGate = new ExitGate(parkingManagerFactory,costComputationFactory);
        Thread.sleep(12000);
        exitGate.exit(vehicle1);
        exitGate.exit(vehicle2);

        System.out.println("Available two wheeler parking "+ TwoWheelerParkingSpotManager.priorityQueue.size());
        System.out.println("Available four wheeler parking "+ FourWheelerParkingSpotManager.priorityQueue.size());
    }
}
