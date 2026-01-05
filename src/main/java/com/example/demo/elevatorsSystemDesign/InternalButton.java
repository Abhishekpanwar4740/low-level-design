package com.example.demo.elevatorsSystemDesign;

public class InternalButton {
    InternalButtonDispatcher buttonDispatcher;

    public void setButtonDispatcher(InternalButtonDispatcher buttonDispatcher) {
        this.buttonDispatcher = buttonDispatcher;
    }

    public void pressButton(int floor){
        buttonDispatcher.submitRequest(floor);
    }
}
