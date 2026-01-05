package com.example.demo.javaConcepts;

public class Doctor extends Person {
    static int qualification=10;
    String designation;

    public Doctor(int noOfHans,String designation) {
        super(noOfHans,noOfHans);
        this.designation=designation;
    }

    @Override
    public int getHands() {
        qualification += 10;
        return qualification;
    }
    public String getDesignation() {
        return designation;
    }
}
