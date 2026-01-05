package com.example.demo.parkingLot.parkingspot;

import com.example.demo.parkingLot.Vehicle;

public class ParkingSpot {
    int id;
    boolean isEmpty;
    Vehicle vehicle;
    int price;

    public Vehicle getVehicle() {
        return vehicle;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int getPrice() {
        return price;
    }

    public void parkVehicle(Vehicle vehicle){
        this.vehicle=vehicle;
        isEmpty=false;
    }
    public void removeVehicle(){
        this.vehicle=null;
        isEmpty=true;
    }
}
