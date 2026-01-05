package com.example.demo.elevatorsSystemDesign;

import java.util.List;

public class FixedFloorDispatcher extends ExternalButtonDispatcher {

    public FixedFloorDispatcher(List<ElevatorController> elevatorControllerList) {
        super(elevatorControllerList);
    }

    @Override
    public void submitRequest(int floor, Direction direction) {
        List<ElevatorController> elevatorControllers = elevatorControllerList.stream().filter(controller -> !controller.elevator.blockFloorList.contains(floor)).toList();
        ElevatorController elevatorController = findOptimalElevator(elevatorControllers, floor, direction);
        elevatorController.acceptNewRequest(floor, direction);
    }
}
