package com.example.demo.elevatorsSystemDesign;

public class Floor {
    int floorId;
    ExternalButton externalButton;

    public Floor(int floorId, ExternalButton externalButton) {
        this.floorId = floorId;
        this.externalButton = externalButton;
    }
}
