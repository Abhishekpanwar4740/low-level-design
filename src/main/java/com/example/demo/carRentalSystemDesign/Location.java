package com.example.demo.carRentalSystemDesign;

public class Location {
    String address;
    String city;
    String state;
    String country;
    int pinCode;

    public Location(String address, String city, String state, String country, int pinCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
    }
}
