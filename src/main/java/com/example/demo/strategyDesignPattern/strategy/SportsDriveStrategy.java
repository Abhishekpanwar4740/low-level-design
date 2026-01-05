package com.example.demo.strategyDesignPattern.strategy;

public class SportsDriveStrategy implements DriveStrategy{
    @Override
    public void drive() {
        System.out.println("Sports drive strategy");
    }
}
