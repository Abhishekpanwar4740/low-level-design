package com.example.demo.parkingLot;

public class Vehicle {
    int vehicleNumber;
    String vehicleType;
    Ticket ticket;
    boolean hasTicket;

    public Vehicle(int vehicleNumber, String vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.hasTicket = false;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
