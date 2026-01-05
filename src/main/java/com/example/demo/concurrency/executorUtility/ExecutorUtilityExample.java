package com.example.demo.concurrency.executorUtility;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ExecutorUtilityExample {
    public static void main(String args[]) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        LocalDateTime currentTime1 = LocalDateTime.now();
        Future<Integer> futureObj = pool.submit(new ComputeSumTask(0, 1000));
        try {
            System.out.println(futureObj.get());
        } catch (Exception e) {
            //exception handling goes here
        }
        LocalDateTime currentTime2 = LocalDateTime.now();
        System.out.println(currentTime1.until(currentTime2, ChronoUnit.NANOS));
        currentTime1 = LocalDateTime.now();
        Integer totalSum = 0;
        for (int i = 0; i <= 1000; i++) {
            totalSum += i;
        }
        System.out.println("Total sum using for loop: " + totalSum);
        currentTime2 = LocalDateTime.now();
        System.out.println(currentTime1.until(currentTime2, ChronoUnit.NANOS));

    }
}
