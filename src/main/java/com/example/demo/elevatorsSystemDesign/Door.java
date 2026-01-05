package com.example.demo.elevatorsSystemDesign;

public class Door {
    Status status;
    Elevator elevator;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }
}
