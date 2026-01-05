package com.example.demo.parkingLot.parkingspot;

public class ParkingManagerFactory {
    public ParkingSpotManager getParkingManager(String vehicleType){
        return switch (vehicleType) {
            case "TwoWheeler" -> new TwoWheelerParkingSpotManager();
            case "FourWheeler" -> new FourWheelerParkingSpotManager();
            default -> null;
        };
    }
}
