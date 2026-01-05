package com.example.demo.strategyDesignPattern;

import com.example.demo.strategyDesignPattern.strategy.SportsDriveStrategy;

public class SportsVehicle extends Vehicle{
    public SportsVehicle() {
        super(new SportsDriveStrategy());
    }
}
