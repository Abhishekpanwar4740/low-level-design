package com.example.demo.concurrency.thread_lifecycle;

public class ProduceTask implements Runnable{
    SharedResource sharedResource;
    ProduceTask(SharedResource resource){
        this.sharedResource=resource;
    }
    @Override
    public void run(){
        System.out.println("Producer thread: "+Thread.currentThread().getName());
        try{
            Thread.sleep(5000l);
        }
        catch (Exception e){
            //Exception handling goes here
        }
        sharedResource.addItem();
    }
}
