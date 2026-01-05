package com.example.demo.elevatorsSystemDesign;

public class InternalButtonDispatcher {
    ElevatorController controller;

    public InternalButtonDispatcher(ElevatorController controller) {
        this.controller = controller;
    }

    public synchronized void submitRequest(int floor) {

        if (controller.elevator.direction.equals(Direction.DOWN) && floor <= controller.elevator.currentFloor)
            controller.acceptNewRequest(floor, controller.elevator.direction);
        else if (controller.elevator.direction.equals(Direction.DOWN))
            controller.acceptNewRequest(floor, Direction.DOWN);
        else if (controller.elevator.direction.equals(Direction.UP) && floor >= controller.elevator.currentFloor)
            controller.acceptNewRequest(floor, controller.elevator.direction);
        else
            controller.acceptNewRequest(floor, Direction.UP);

    }
}
