package com.example.demo.strategyDesignPattern;

import com.example.demo.strategyDesignPattern.strategy.NormalDriveStrategy;

public class OffRoadVehicle extends Vehicle{
    public OffRoadVehicle() {
        super(new NormalDriveStrategy());
    }
}
