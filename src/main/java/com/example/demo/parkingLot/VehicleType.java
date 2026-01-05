package com.example.demo.parkingLot;

public enum VehicleType {
    TWOWHEELER("TwoWheeler"),
    FOURWHEELER("FourWheeler");
    String value;

    VehicleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
