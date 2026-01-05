package com.example.demo.parkingLot.parkingspot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FourWheelerParkingSpotManager extends ParkingSpotManager {
    static Comparator<ParkingSpot> byPriority = Comparator.comparingInt(ParkingSpot::getId);
    public static PriorityQueue<ParkingSpot> priorityQueue = new PriorityQueue<>(byPriority);

    public FourWheelerParkingSpotManager() {
        super(new ArrayList<>(500), priorityQueue);
    }
}
