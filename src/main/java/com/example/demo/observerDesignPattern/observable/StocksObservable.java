package com.example.demo.observerDesignPattern.observable;

import com.example.demo.observerDesignPattern.observer.NotificationAlertObserver;

import java.util.ArrayList;
import java.util.List;

public interface StocksObservable {
    public void add(NotificationAlertObserver observer);

    public void remove(NotificationAlertObserver observer);

    public void notifySubscriber();

    public void setStockCount(int newStockCount);

    public int getStockCount();

}
