package com.example.demo.elevatorsSystemDesign;

import java.util.List;


public class Elevator {
    int elevatorId;
    Direction direction;
    Status status;
    int currentFloor;
    Door door;
    InternalButton internalButton;
    List<Integer> blockFloorList;

    public Elevator(int elevatorId, Direction direction, Status status, int currentFloor, Door door, InternalButton internalButton) {
        this.elevatorId = elevatorId;
        this.direction = direction;
        this.status = status;
        this.currentFloor = currentFloor;
        this.door = door;
        this.internalButton = internalButton;
    }

    public void move(int destination, Direction dir) {
        if (dir.equals(Direction.DOWN)) {
            for (int i = currentFloor - 1; i >= destination; i--) {
                System.out.println("current floor: " + i + " GOING: " + dir.toString());
            }
        } else {
            for (int i = currentFloor + 1; i <= destination; i++) {
                System.out.println("current floor: " + i + " GOING: " + dir.toString());
            }
        }
        setCurrentFloor(destination);
        System.out.println("Opening gates of lift: " + elevatorId);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Closing gates of lift: " + elevatorId);

    }

    public void openDoor(int destination) {
        door.setStatus(Status.OPEN);
    }

    public void closeDoor(int destination) {
        door.setStatus(Status.CLOSE);
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public InternalButton getInternalButton() {
        return internalButton;
    }

    public void setInternalButton(InternalButton internalButton) {
        this.internalButton = internalButton;
    }

    public List<Integer> getBlockFloorList() {
        return blockFloorList;
    }

    public void setBlockFloorList(List<Integer> blockFloorList) {
        this.blockFloorList = blockFloorList;
    }
}
