package com.example.demo.concurrency.thread_lifecycle;

public class SharedResource {
    boolean itemAvailable=false;

    public synchronized void addItem(){
        itemAvailable=true;
        System.out.println("Item added by: "+Thread.currentThread().getName()+" and invoking all threads which are waiting");
        notifyAll();
    }
    public synchronized void consumeItem(){
        System.out.println("ConsumeItem method invoked by: "+Thread.currentThread().getName());

        //using while loop to avoid "spurious wake-up", sometimes because of system noise
        while(!itemAvailable){
            try {
                System.out.println("thread "+Thread.currentThread().getName()+" is waiting now.");
                wait();
            }
            catch (Exception e){
                //Exception handling goes here
            }
        }
        System.out.println("Item Consumed by: "+Thread.currentThread().getName());
        itemAvailable=false;
    }
}
