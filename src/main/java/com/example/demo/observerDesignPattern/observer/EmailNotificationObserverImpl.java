package com.example.demo.observerDesignPattern.observer;

import com.example.demo.observerDesignPattern.observable.StocksObservable;

public class EmailNotificationObserverImpl implements NotificationAlertObserver {
    StocksObservable observable;
    String emailId;

    public EmailNotificationObserverImpl(StocksObservable observable, String emailId) {
        this.observable = observable;
        this.emailId = emailId;
    }

    @Override
    public void update() {
        System.out.println("Email sent to user with email " + emailId);
    }
}
