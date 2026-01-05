package com.example.demo.adaptorDesignPattern.Adaptor;

import com.example.demo.adaptorDesignPattern.Adaptee.WeightMachine;

public class WeightMachineAdaptorImpl implements WeightMachineAdaptor{
    WeightMachine weightMachine;

    public WeightMachineAdaptorImpl(WeightMachine weightMachine) {
        this.weightMachine = weightMachine;
    }

    @Override
    public double weightInKg() {
        double weightInPounds=weightMachine.getWeightInPound();
        double weightInKg=weightInPounds*.45;
        return weightInKg;
    }
}
