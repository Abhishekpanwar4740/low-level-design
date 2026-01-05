package com.example.demo.observerDesignPattern;

import com.example.demo.observerDesignPattern.observable.IphoneObservableImpl;
import com.example.demo.observerDesignPattern.observable.StocksObservable;
import com.example.demo.observerDesignPattern.observer.EmailNotificationObserverImpl;
import com.example.demo.observerDesignPattern.observer.MobileObserverImpl;
import com.example.demo.observerDesignPattern.observer.NotificationAlertObserver;

public class Main {
    public static void main(String[] args) {

        //Observer Design Principle
        StocksObservable iphoneStockObservable=new IphoneObservableImpl();

        NotificationAlertObserver observer1=new EmailNotificationObserverImpl(iphoneStockObservable,"abhi@gmail.com");
        NotificationAlertObserver observer2=new MobileObserverImpl(iphoneStockObservable,"abhishekKumar");
        NotificationAlertObserver observer3=new EmailNotificationObserverImpl(iphoneStockObservable,"komal@gmail.com");

        iphoneStockObservable.add(observer1);
        iphoneStockObservable.add(observer2);
        iphoneStockObservable.add(observer3);

        iphoneStockObservable.setStockCount(5);

    }
}