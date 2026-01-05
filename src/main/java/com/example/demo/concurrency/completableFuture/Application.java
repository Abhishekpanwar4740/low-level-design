package com.example.demo.concurrency.completableFuture;

import java.util.concurrent.*;

public class Application {
    public static void main(String args[]) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

//         thenApply and thenApplyAsync
//        try {
//            CompletableFuture<String> asyncTask1 = CompletableFuture.supplyAsync(() -> {
//                System.out.println("ThreadName of SupplyAsync " + Thread.currentThread().getName());
//                return "task completed";
//            }, poolExecutor);
//            CompletableFuture<String> asyncTask2 = asyncTask1.thenApply((String value) -> {
//                try {
//                    Thread.sleep(2000);
//                } catch (Exception e) {
//                    //Exception handling goes here
//                }
//
//                return value + " sync task " + Thread.currentThread().getName();
//            });
//            System.out.println(asyncTask1.get());
//            CompletableFuture<String> asyncTask3 = asyncTask1.thenApplyAsync((String value) -> {
//                return value + " Async task " + Thread.currentThread().getName();
//            }, poolExecutor);
//            System.out.println(asyncTask1.get());
//            System.out.println(asyncTask2.get());
//            System.out.println(asyncTask3.get());
//
//        } catch (Exception e) {
//            //exception handling goes here
//        }
//         thenCompose and thenComposeAsync ordering of async tasks is maintained
//        try {
//            CompletableFuture<String> asyncTask1 = CompletableFuture.supplyAsync(() -> {
//                System.out.println("ThreadName of SupplyAsync " + Thread.currentThread().getName());
//                return "hello";
//            });
//            CompletableFuture<String> asyncTask2 = asyncTask1.thenCompose((String value) -> {
//                return CompletableFuture.supplyAsync(() -> value + " world");
//            });
//
//            CompletableFuture<String> asyncTask3 = asyncTask2.thenComposeAsync((String value) -> {
//                return CompletableFuture.supplyAsync(() -> value + " All");
//            }, poolExecutor);
//            System.out.println(asyncTask1.get());
//            System.out.println(asyncTask2.get());
//            System.out.println(asyncTask3.get());
//
//        } catch (Exception e) {
//            //exception handling goes here
//        }

        //thenAccept and thenAcceptAsync
//        try {
//            CompletableFuture<String> asyncTask1 = CompletableFuture.supplyAsync(() -> {
//                System.out.println("ThreadName of SupplyAsync " + Thread.currentThread().getName());
//                return "hello";
//            });
//
//            CompletableFuture<Void> asyncTask2 = asyncTask1.thenAcceptAsync((String val) -> {
//                System.out.println("ThreadName of SupplyAsync " + Thread.currentThread().getName());
//                System.out.println("Task completed acknowledgement");
//            }, poolExecutor);
//
//            System.out.println(asyncTask1.get());
//            System.out.println(asyncTask2.get());
//
//        } catch (Exception e) {
//            //exception handling goes here
//        }

        //thenCombine and thenCombineAsync
        try {
            CompletableFuture<Integer> asyncTask1 = CompletableFuture.supplyAsync(() -> {
                System.out.println("ThreadName of SupplyAsync " + Thread.currentThread().getName());
                return 10;
            });
            CompletableFuture<String> asyncTask2 = CompletableFuture.supplyAsync(() -> {
                System.out.println("ThreadName of SupplyAsync " + Thread.currentThread().getName());
                return "world";
            });

            CompletableFuture<String> asyncTask3 = asyncTask1.thenCombineAsync(asyncTask2, (Integer value, String number) -> {
                System.out.println("ThreadName of SupplyAsync " + Thread.currentThread().getName());
                return value + number;
            },poolExecutor);
            System.out.println(asyncTask1.get());
            System.out.println(asyncTask2.get());
            System.out.println(asyncTask3.get());

        } catch (Exception e) {
            //exception handling goes here
        }


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