package com.example.demo.elevatorsSystemDesign;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {
    public static void main(String[] args) {
        List<Floor> floorList = new ArrayList<>();
        int noOfFloors = 10;
        int noOfLifts = 10;
        List<ElevatorController> elevatorControllerList = new ArrayList<>();
        for (int i = 0; i < noOfLifts; i++) {
            Door door = new Door();
            InternalButton internalButton = new InternalButton();
            Elevator elevator = new Elevator(i + 1, Direction.UP, Status.STATIC, 0, door, internalButton);
            ElevatorController elevatorController = new ElevatorController(elevator);
            InternalButtonDispatcher internalButtonDispatcher = new InternalButtonDispatcher(elevatorController);
            internalButton.setButtonDispatcher(internalButtonDispatcher);
            elevatorControllerList.add(elevatorController);
        }
        Building building = new Building(floorList);
        List<ExternalButton> externalButtons = new ArrayList<>();
        ExternalButtonDispatcher externalButtonDispatcher = new ExternalButtonDispatcher(elevatorControllerList);
        for (int i = 0; i < noOfFloors; i++) {
            ExternalButton externalButton = new ExternalButton(externalButtonDispatcher, i);
            Floor floor = new Floor(i, externalButton);
            externalButtons.add(externalButton);
            floorList.add(floor);
        }

//        try {
        externalButtons.get(2).pressButton(Direction.UP);
        //Thread.sleep(2000); // Pause for 2 seconds
        externalButtons.get(5).pressButton(Direction.UP);
        externalButtons.get(2).pressButton(Direction.DOWN);
        //Thread.sleep(2000);
        elevatorControllerList.get(2).elevator.internalButton.pressButton(7);
        //Thread.sleep(2000);
        externalButtons.get(7).pressButton(Direction.DOWN);

//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (ElevatorController elevatorController : elevatorControllerList) {
            System.out.println("Elevator id: " + elevatorController.elevator.elevatorId);
            elevatorController.run();
        }

    }
}
