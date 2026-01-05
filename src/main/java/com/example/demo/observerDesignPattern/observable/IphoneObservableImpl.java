package com.example.demo.observerDesignPattern.observable;

import com.example.demo.observerDesignPattern.observer.NotificationAlertObserver;

import java.util.ArrayList;
import java.util.List;

public class IphoneObservableImpl implements StocksObservable {
    List<NotificationAlertObserver> observerList = new ArrayList<>();
    public int stockCount = 0;

    @Override
    public void add(NotificationAlertObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void remove(NotificationAlertObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifySubscriber() {
        for(NotificationAlertObserver observer:observerList){
            observer.update();
        }
    }

    @Override
    public void setStockCount(int newStockCount) {
        if(stockCount==0){
            notifySubscriber();
        }
        stockCount+=newStockCount;
    }

    @Override
    public int getStockCount() {
        return stockCount;
    }
}
