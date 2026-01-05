package com.example.demo.liskovSubstitutionPrinciple;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Liskov substitution principle example
        List<Vehicle> vehicles=new ArrayList<>();
        vehicles.add(new Car());
        vehicles.add(new Bicycle());
        vehicles.add(new MotorCycle());
        for(Vehicle vehicle:vehicles){
            System.out.println(vehicle.getNumberOfWheels().toString());
            //System.out.println(vehicle.hasEngine());
        }

        List<EngineVehicle> engineVehicles=new ArrayList<>();
        engineVehicles.add(new Car());
        //engineVehicles.add(new Bicycle());
        engineVehicles.add(new MotorCycle());
        for(EngineVehicle vehicle:engineVehicles){
            System.out.println(vehicle.getNumberOfWheels().toString());
            System.out.println(vehicle.hasEngine());
        }
    }
}
