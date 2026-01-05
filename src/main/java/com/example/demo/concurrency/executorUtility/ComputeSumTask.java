package com.example.demo.concurrency.executorUtility;

import java.util.concurrent.RecursiveTask;

public class ComputeSumTask extends RecursiveTask<Integer> {
    int start;
    int end;

    ComputeSumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= 4) {
            int totalSum = 0;
            for (int i = start; i <= end; i++) {
                totalSum += i;
            }
//            System.out.println("Base Thread used : " + totalSum + " " + Thread.currentThread().getName());
            return totalSum;
        } else {
            //split the task
            int mid = (start + end) / 2;
            ComputeSumTask leftTask = new ComputeSumTask(start, mid);
            ComputeSumTask rightTask = new ComputeSumTask(mid + 1, end);
//            System.out.println("Thread used : " + Thread.currentThread().getName());
            //Fork the subtasks for parallel execution
            leftTask.fork();
            rightTask.fork();

            //Combine the results of subtasks
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            return leftResult + rightResult;
        }
    }
}
