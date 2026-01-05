package com.example.demo.concurrency.monitor_lock;

public class Application {
    public static void main(String args[]) throws InterruptedException {
        MonitorLockExample obj = new MonitorLockExample();
        Thread t1 = new Thread(() -> {
            obj.task1();
        });
        Thread t2 = new Thread(() -> {
            obj.task2();
        });
        Thread t3 = new Thread(() -> {
            obj.task3();
        });

        t1.start();
        t2.start();
        Thread.sleep(2000);
        t3.start();
    }
}
