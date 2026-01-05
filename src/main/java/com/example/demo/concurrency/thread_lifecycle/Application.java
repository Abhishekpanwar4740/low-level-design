package com.example.demo.concurrency.thread_lifecycle;

public class Application {
    public static void main(String args[]) {
        System.out.println("Main method starts: " + Thread.currentThread().getName());
        SharedResource sharedResource = new SharedResource();
        Thread producerThread = new Thread(new ProduceTask(sharedResource));
        Thread consumerThread = new Thread(new ConsumeTask(sharedResource));
        producerThread.start();
        consumerThread.start();
    }
}
