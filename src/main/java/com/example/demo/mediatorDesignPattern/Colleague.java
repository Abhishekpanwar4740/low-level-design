package com.example.demo.mediatorDesignPattern;

public interface Colleague {
    public void placeBid(int bidAmount);
    public void receiveBidNotification(int bidAmount);
    String getName();
}
