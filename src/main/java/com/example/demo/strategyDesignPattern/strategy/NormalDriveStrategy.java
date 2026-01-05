package com.example.demo.strategyDesignPattern.strategy;

public class NormalDriveStrategy implements DriveStrategy{
    @Override
    public void drive() {
        System.out.println("Sports drive strategy");
    }
}
