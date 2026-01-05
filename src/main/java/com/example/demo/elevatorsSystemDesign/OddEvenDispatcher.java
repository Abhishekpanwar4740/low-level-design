package com.example.demo.elevatorsSystemDesign;

import java.util.List;

public class OddEvenDispatcher extends ExternalButtonDispatcher {
    public OddEvenDispatcher(List<ElevatorController> elevatorControllerList) {
        super(elevatorControllerList);
    }

    @Override
    public void submitRequest(int floor, Direction direction) {
        List<ElevatorController> elevatorControllers = elevatorControllerList.stream().filter(controller -> isEven(floor) == isEven(controller.elevator.elevatorId)).toList();
        ElevatorController elevatorController = findOptimalElevator(elevatorControllers, floor, direction);
        elevatorController.acceptNewRequest(floor, direction);
    }

    public boolean isEven(int liftId) {
        return liftId % 2 == 0;
    }
}
