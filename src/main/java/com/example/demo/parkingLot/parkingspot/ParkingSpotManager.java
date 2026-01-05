package com.example.demo.parkingLot.parkingspot;

import com.example.demo.parkingLot.Vehicle;
import com.example.demo.parkingLot.parkingStrategy.ParkingStrategy;

import java.util.List;
import java.util.PriorityQueue;

public class ParkingSpotManager {
    List<ParkingSpot> parkingSpotList;
    public PriorityQueue<ParkingSpot> priorityQueue;

    public ParkingSpotManager(List<ParkingSpot> parkingSpotList,PriorityQueue<ParkingSpot> priorityQueue) {
        this.parkingSpotList = parkingSpotList;
        this.priorityQueue=priorityQueue;
    }

    public List<ParkingSpot> getParkingSpotList() {
        return parkingSpotList;
    }

    public ParkingSpot findParkingSpace(String vehicleType, ParkingStrategy parkingStrategy) {
        ParkingSpot parkingSpot = priorityQueue.peek();
        priorityQueue.remove(parkingSpot);
        return parkingSpot;
    }

    public void addParkingSpace(ParkingSpot parkingSpot) {
        priorityQueue.add(parkingSpot);
        parkingSpotList.add(parkingSpot);
    }

    public void removeParkingSpace(ParkingSpot parkingSpot) {
        parkingSpotList.remove(parkingSpot);
        priorityQueue.remove(parkingSpot);
    }

    public void parkVehicle(Vehicle vehicle, ParkingSpot parkingSpot) {
        parkingSpot.parkVehicle(vehicle);
    }

    public void removeVehicle(ParkingSpot parkingSpot) {
        parkingSpot.removeVehicle();
    }
}
