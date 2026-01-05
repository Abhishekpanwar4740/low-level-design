package com.example.demo.elevatorsSystemDesign;

public class ExternalButton {
    ExternalButtonDispatcher buttonDispatcher;
    int floor;

    public ExternalButton(ExternalButtonDispatcher buttonDispatcher, int floor) {
        this.buttonDispatcher = buttonDispatcher;
        this.floor = floor;
    }

    public void pressButton(Direction direction) {
        buttonDispatcher.submitRequest(floor, direction);
    }
}
