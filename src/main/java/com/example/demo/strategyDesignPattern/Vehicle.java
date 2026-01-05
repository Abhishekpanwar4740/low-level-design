package com.example.demo.strategyDesignPattern;

import com.example.demo.strategyDesignPattern.strategy.DriveStrategy;

public class Vehicle {
    DriveStrategy driveStrategy;
    //This known as constructor injection
    public Vehicle(DriveStrategy driveStrategy) {
        this.driveStrategy = driveStrategy;
    }
    void drive(){
        driveStrategy.drive();
    }
}
