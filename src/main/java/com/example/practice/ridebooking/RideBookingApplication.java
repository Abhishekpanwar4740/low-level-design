package com.example.practice.ridebooking;

import com.example.practice.ridebooking.dto.*;
import com.example.practice.ridebooking.service.FindDriverStrategy;
import com.example.practice.ridebooking.service.NearestDriverStrategy;
import com.example.practice.ridebooking.service.RideSharingService;
import com.example.practice.ridebooking.service.RideSharingServiceImpl;
import org.springframework.data.util.Pair;

import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

public class RideBookingApplication {
//    Case 1 - Basic flow test with 1 user and 2 drivers. User books a ride and we observe the async status changes in the background while main thread is free to do other things (like updating the UI).
//    public static void main(String[] args) throws InterruptedException {
//        RideSharingService service = new RideSharingServiceImpl();
//
//        // 1. Create and Register a User
//        User user1 = new User();
//        user1.setName("Alice");
//        user1.setCordinates(Pair.of(0, 0));
//        user1.setPreferredDriverIds(new HashSet<>()); // Initialize empty set to avoid NullPointer
//        service.registerUser(user1);
//        System.out.println("Registered User: " + user1.getName() + " [ID: " + user1.getId() + "]");
//
//        // 2. Create and Register Drivers
//        Driver driver1 = new Driver();
//        driver1.setName("Bob");
//        driver1.setCordinates(Pair.of(1, 1)); // Closest to Alice
//        driver1.setExperienceInDays(100L);
//        service.registerDriver(driver1);
//
//        Driver driver2 = new Driver();
//        driver2.setName("Charlie");
//        driver2.setCordinates(Pair.of(5, 5)); // Further away
//        driver2.setExperienceInDays(500L);
//        service.registerDriver(driver2);
//
//        System.out.println("Registered Drivers: " + driver1.getName() + ", " + driver2.getName() + "\n");
//
//        // 3. Setup Strategy and Book the Ride
//        FindDriverStrategy strategy = new NearestDriverStrategy();
//
//        System.out.println("--> User Alice is clicking 'Book Ride'...");
//        Pair<Integer, Integer> source = Pair.of(0, 0);
//        Pair<Integer, Integer> destination = Pair.of(10, 10);
//
//        // This will return immediately!
//        Ride ride = service.bookRide(user1, source, destination, strategy);
//
//        System.out.println("--> 'bookRide' returned immediately!");
//        System.out.println("--> Current Ride Status in Main Thread: " + ride.getStatus() + "\n");
//
//        // 4. Poll the Ride object to observe the Async background changes
//        System.out.println("Main thread is now free to do other things (like updating the UI)...");
//        for (int i = 1; i <= 15; i++) {
//            Thread.sleep(1000); // Wait 1 second
//            System.out.print("[Second " + i + "] UI Polling Ride Status: " + ride.getStatus());
//
//            if (ride.getStatus() == RideStatusEnum.DRIVER_ASSIGNED) {
//                System.out.println(" -> Assigned to Driver ID: " + ride.getDriverId());
//                break; // Stop polling once assigned
//            } else if (ride.getStatus() == RideStatusEnum.CANCELLED) {
//                System.out.println(" -> Ride was cancelled.");
//                break;
//            } else {
//                System.out.println("Ride status is still: " + ride.getStatus());
//            }
//        }
//
//        // 5. Test History
//        System.out.println("\n--- Final Ride History ---");
//        System.out.println("User History Count: " + service.get_user_ride_history(user1.getId()).size());
//
//        // Note: For a real application, you'd want a graceful shutdown of the ExecutorService here.
//        System.exit(0);
//    }
//Case2 - concurency test with 1 driver and 2 users trying to book at the same time
    public static void main(String[] args) throws InterruptedException {
        RideSharingService service = new RideSharingServiceImpl();

        // 1. Register ONLY ONE Driver
        Driver driver1 = new Driver();
        driver1.setName("Bob");
        driver1.setCordinates(Pair.of(1, 1));
        driver1.setExperienceInDays(100L);
        service.registerDriver(driver1);
        System.out.println("Registered Driver: " + driver1.getName() + " [ID: " + driver1.getId() + "]\n");

        // 2. Register TWO Users in the exact same location
        User user1 = new User();
        user1.setName("Alice");
        user1.setCordinates(Pair.of(0, 0));
        user1.setPreferredDriverIds(new HashSet<>());
        service.registerUser(user1);

        User user2 = new User();
        user2.setName("Charlie");
        user2.setCordinates(Pair.of(0, 0));
        user2.setPreferredDriverIds(new HashSet<>());
        service.registerUser(user2);

        System.out.println("Registered Users: Alice and Charlie. Both want a ride right now!");
        System.out.println("--------------------------------------------------------------");

        FindDriverStrategy strategy = new NearestDriverStrategy();
        Pair<Integer, Integer> dest = Pair.of(10, 10);

        // 3. Setup the Concurrency Test
        // Latch acts as a starting gun so both threads fire at the EXACT same time
        CountDownLatch startingGun = new CountDownLatch(1);

        // Arrays to hold the async ride results so main thread can print them later
        Ride[] aliceRide = new Ride[1];
        Ride[] charlieRide = new Ride[1];

        // Thread for Alice
        Thread aliceThread = new Thread(() -> {
            try {
                startingGun.await(); // Wait for the starting gun
                aliceRide[0] = service.bookRide(user1, Pair.of(0, 0), dest, strategy);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Thread for Charlie
        Thread charlieThread = new Thread(() -> {
            try {
                startingGun.await(); // Wait for the starting gun
                charlieRide[0] = service.bookRide(user2, Pair.of(0, 0), dest, strategy);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start both threads (they will pause at the starting gun)
        aliceThread.start();
        charlieThread.start();

        // 4. FIRE THE STARTING GUN! Both users call bookRide simultaneously.
        startingGun.countDown();

        // Wait for the main thread to ensure the async methods have time to finish
        System.out.println("Race condition triggered! Waiting for background threads to resolve...");
        Thread.sleep(15000); // Wait 15 seconds to let the drivers "accept/reject"

        // 5. Evaluate the Results
        System.out.println("\n--------------------------------------------------------------");
        System.out.println("FINAL RESULTS:");

        System.out.println("Alice's Ride Status: " + aliceRide[0].getStatus());
        if (aliceRide[0].getDriverId() != null) {
            System.out.println(" -> Assigned Driver: " + aliceRide[0].getDriverId());
        }

        System.out.println("Charlie's Ride Status: " + charlieRide[0].getStatus());
        if (charlieRide[0].getDriverId() != null) {
            System.out.println(" -> Assigned Driver: " + charlieRide[0].getDriverId());
        }

        // Only ONE user should get the driver. The other should be CANCELLED/ASSIGNING_DRIVER
        if (aliceRide[0].getDriverId() != null && charlieRide[0].getDriverId() != null) {
            if (aliceRide[0].getDriverId().equals(charlieRide[0].getDriverId())) {
                System.err.println("❌ CRITICAL BUG: Double Booking Occurred!");
            }
        } else {
            System.out.println("✅ SUCCESS: Concurrency handled correctly. No double booking.");
        }

        System.exit(0);
    }
}
