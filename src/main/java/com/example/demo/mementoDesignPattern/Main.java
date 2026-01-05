package com.example.demo.mementoDesignPattern;

public class Main {
    public static void main(String[] args){
        ConfigurationCareTaker configurationCareTaker=new ConfigurationCareTaker();
        ConfigurationOriginator configurationOriginator=new ConfigurationOriginator(2,10);

        ConfigurationMemento snapshot1=configurationOriginator.createMemento();

        configurationCareTaker.addMemento(snapshot1);
        configurationOriginator.setHeight(3);
        configurationOriginator.setWidth(11);

        ConfigurationMemento snapshot2=configurationOriginator.createMemento();

        configurationCareTaker.addMemento(snapshot2);

        configurationOriginator.setHeight(13);
        configurationOriginator.setWidth(111);

        configurationOriginator.restoreMemento(configurationCareTaker.undo());

        System.out.println("height: "+configurationOriginator.height+" width: "+configurationOriginator.width);

    }
}
