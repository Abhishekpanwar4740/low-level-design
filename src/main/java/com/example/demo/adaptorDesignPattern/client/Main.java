package com.example.demo.adaptorDesignPattern.client;

import com.example.demo.adaptorDesignPattern.Adaptee.WeightMachineForBabies;
import com.example.demo.adaptorDesignPattern.Adaptor.WeightMachineAdaptor;
import com.example.demo.adaptorDesignPattern.Adaptor.WeightMachineAdaptorImpl;

public class Main {
    public static void main(String args[]){

        WeightMachineAdaptor weightMachineAdapter = new WeightMachineAdaptorImpl(new WeightMachineForBabies());
        System.out.println(weightMachineAdapter.weightInKg());
    }

}
