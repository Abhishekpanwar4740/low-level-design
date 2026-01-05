package com.example.demo.concurrency.producer_consumer;


public class Application {
    public static void main(String args[]) {
        System.out.println("Main method starts: " + Thread.currentThread().getName());
        SharedResource sharedResource = new SharedResource(3);
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i <= 6; i++) {
                    sharedResource.produce(i);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i <= 6; i++) {
                    sharedResource.consume();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        producerThread.start();
        consumerThread.start();
    }
}
