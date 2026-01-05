package com.example.demo.concurrency.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Application {
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        List<Future<?>> futureObjects = new ArrayList<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(2), new CustomThreadFactory(), new CustomRejectHandler());
        for (int i = 0; i <= 7; i++) {
            int finalI = i;
            Future<?> futureObj = executor.submit(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Task " + finalI + " processed by: " + Thread.currentThread().getName());

            });
            futureObjects.add(futureObj);
            System.out.println("Task " + finalI + " is done " + futureObj.isDone());
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // custom logic goes here;
        }
        for (int i = 0; i < futureObjects.size(); i++) {
            int finalI = i;
            System.out.println("Task " + finalI + " is done " + futureObjects.get(i).isDone());
        }
        executor.shutdown();
    }
}

class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r);
        th.setDaemon(false);
        th.setPriority(Thread.NORM_PRIORITY);
        return th;
    }
}

class CustomRejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task rejected: " + r.toString());
    }
}
