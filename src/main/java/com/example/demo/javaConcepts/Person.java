package com.example.demo.javaConcepts;

public class Person {
    int noOfHans;
    static int noOfLegs;
    public Person(int noOfHans,int legs){
        this.noOfHans=noOfHans;
        noOfLegs=legs;
    }
    public int getHands(){
        return noOfHans;
    }
}
