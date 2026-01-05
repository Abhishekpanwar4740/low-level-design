package com.example.demo.javaConcepts;

public class Main {
    public static void main(String[] args){
        Person person1=new Person(10,30);
        System.out.println(Person.noOfLegs);
        Person person2=new Person(20,40);
        System.out.println(Person.noOfLegs);
        System.out.println(person1.getHands());
        System.out.println(person2.getHands());
    }
}
