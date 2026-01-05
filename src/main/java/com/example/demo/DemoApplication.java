package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

			// Create a fixed-size thread pool with 4 threads
//			ExecutorService executor = Executors.newFixedThreadPool(4);
//
//			// Submit tasks to the executor
//			for (int i = 0; i < 10; i++) {
//				final int taskNumber = i;
//				executor.execute(() -> {
//					System.out.println("Task " + taskNumber + " executed by thread " + Thread.currentThread().getName());
//				});
//			}
//
//			// Shutdown the executor when tasks are done
//			executor.shutdown();

	}

}
