package com.example.demo.observerDesignPattern.observer;

import com.example.demo.observerDesignPattern.observable.StocksObservable;

public class MobileObserverImpl implements NotificationAlertObserver {
    StocksObservable observable;
    String userName;

    public MobileObserverImpl(StocksObservable observable, String userName) {
        this.observable = observable;
        this.userName = userName;
    }

    @Override
    public void update() {
        System.out.println("Message sent to user with user name " + userName);
    }
}
