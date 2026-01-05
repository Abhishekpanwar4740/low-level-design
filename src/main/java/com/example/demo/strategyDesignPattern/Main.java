package com.example.demo.strategyDesignPattern;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello, World!");

        //stragey design pattern
        Vehicle vehicle=new SportsVehicle();
        vehicle.drive();
    }
}