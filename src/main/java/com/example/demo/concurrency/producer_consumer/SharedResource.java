package com.example.demo.concurrency.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;

public class SharedResource {
    private Queue<Integer> sharedBuffer;
    private int bufferSize;

    public SharedResource(int bufferSize) {
        this.sharedBuffer = new LinkedList<>();
        this.bufferSize = bufferSize;
    }

    public synchronized void produce(int item) throws Exception{
        // If buffer is full, wait for the consumer to consume items
        while(sharedBuffer.size()==bufferSize){
            System.out.println("Buffer is full, Producer is waiting for consumer");
            wait();
        }
        sharedBuffer.add(item);
        System.out.println("Produced: "+item);
        notify();
    }
    public synchronized int consume() throws Exception{
        //Buffer is empty, wait for the producer to produce items
        while(sharedBuffer.isEmpty()){
            System.out.println("Buffer is empty, Consumer is waiting for Producer");
            wait();
        }
        int item=sharedBuffer.poll();
        System.out.println("Consumed: "+item);
        notify();
        return item;
    }
}
