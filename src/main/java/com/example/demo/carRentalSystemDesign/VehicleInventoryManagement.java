package com.example.demo.carRentalSystemDesign;

import com.example.demo.carRentalSystemDesign.product.Vehicle;

import java.util.List;

public class VehicleInventoryManagement {
    List<Vehicle> vehicles;

    public VehicleInventoryManagement(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
