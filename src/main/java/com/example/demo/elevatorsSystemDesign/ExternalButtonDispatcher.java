package com.example.demo.elevatorsSystemDesign;

import java.util.List;

public class ExternalButtonDispatcher {
    List<ElevatorController> elevatorControllerList;

    public ExternalButtonDispatcher(List<ElevatorController> elevatorControllerList) {
        this.elevatorControllerList = elevatorControllerList;
    }

    public void submitRequest(int floor, Direction direction) {
        ElevatorController elevatorController = findOptimalElevator(elevatorControllerList, floor, direction);
        elevatorController.acceptNewRequest(floor, direction);
    }

    ElevatorController findOptimalElevator(List<ElevatorController> elevatorControllers, int floor, Direction direction) {
        ElevatorController optimalElevatorController = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorController elevatorController : elevatorControllers) {
            int distance = Math.abs(floor - elevatorController.elevator.currentFloor);
            if (distance < minDistance) {
                minDistance = distance;
                optimalElevatorController = elevatorController;
            }
        }

        return optimalElevatorController;
    }
}
