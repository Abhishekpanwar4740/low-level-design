package com.example.demo.handleNullObjects;

public class Main {
    public static void main(String[] arg) {
        Vehicle vehicle = VehicleFactory.getVehicleObject("Bike");
        printVehicleDetails(vehicle);
        Vehicle car = VehicleFactory.getVehicleObject("Car");
        printVehicleDetails(car);
    }

    private static void printVehicleDetails(Vehicle vehicle) {

            System.out.println("Seating capacity: " + vehicle.getSeatingCapacity());
            System.out.println("Tank capacity: " + vehicle.getTankCapacity());

    }
}
