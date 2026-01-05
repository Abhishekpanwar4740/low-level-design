package com.example.demo.handleNullObjects;

public class VehicleFactory {
    static Vehicle getVehicleObject(String type){
        if("Car".equals(type)){
            return new Car();
        }
        return new NullVehicle();
    }
}
