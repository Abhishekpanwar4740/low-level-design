package com.example.practice.ticketbooking;

import com.example.practice.ridebooking.service.FindDriverStrategy;
import com.example.practice.ridebooking.service.NearestDriverStrategy;
import com.example.practice.ticketbooking.dto.*;
import com.example.practice.ticketbooking.exception.SeatAlreadyBookedException;
import com.example.practice.ticketbooking.service.BookingService;
import com.example.practice.ticketbooking.service.FlightService;
import com.example.practice.ticketbooking.service.PaymentService;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;




import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

    public class TicketBooking {
//        public static void main(String[] args) {
//            System.out.println("Welcome to the Ticket Booking System!");
//            List<Flight> flights = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                Flight flight = new Flight();
//                List<Seat> seats = new ArrayList<>();
//                for (int j = 0; j < 100; j++) {
//                    Seat seat = new Seat();
//                    seat.setFlightId("FL" + i);
//                    seat.setSeatId((j / 6 + 1) + String.valueOf((char) ('A' + j % 6)));
//                    seat.setSeatNumber((j / 6 + 1) + String.valueOf((char) ('A' + j % 6)));
//                    seat.setStatus(SeatStatus.IS_AVAILABLE);
//                    seat.setPrice(1000.0);
//                    seats.add(seat);
//                }
//                flight.setFlightNumber("FL" + i);
//                flight.setDeparture("City" + i);
//                flight.setArrival(i + "City");
//                flight.setAllSeats(seats);
//                flight.setArrivalTime(new Timestamp(1000000000 + i * 10000000));
//                flight.setDepartureTime(new Timestamp(1000000000 + i * 10000000 - 3600000));
//                flights.add(flight);
//            }
//            FlightService flightService = new FlightService(flights);
//            PaymentService paymentService = new PaymentService();
//            BookingService bookingService = new BookingService(flightService, paymentService);
//
//            User user1 = new User();
//            user1.setName("Alice");
//            user1.setId(UUID.randomUUID().toString());
//            User user2 = new User();
//            user2.setName("Charlie");
//            user2.setId(UUID.randomUUID().toString());
//            flightService.getSeatsForFlight("FL1").forEach(seat -> {
//                System.out.println("Seat: " + seat.getSeatNumber() + " | Status: " + seat.getStatus());
//            });
//
//            System.out.println("Registered Users: Alice and Charlie. Both want a book seat now!");
//            System.out.println("--------------------------------------------------------------");
//
//            // 3. Setup the Concurrency Test
//            // Latch acts as a starting gun so both threads fire at the EXACT same time
//            CountDownLatch startingGun = new CountDownLatch(1);
//            ;
//
//            // Thread for Alice
//            Thread aliceThread = new Thread(() -> {
//                try {
//                    startingGun.await(); // Wait for the starting gun
//                    Booking aliceBooking = bookingService.bookSeats("FL1", user1.getId(), List.of("1A", "1B", "1C"));
//                    System.out.println("alice booked seats: " + aliceBooking.getSeats());
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }catch (SeatAlreadyBookedException e) {
//                    System.out.println("Charlie failed to book seats: " + e.getMessage());
//                }
//            });
//
//            // Thread for Charlie
//            Thread charlieThread = new Thread(() -> {
//                try {
//                    startingGun.await(); // Wait for the starting gun
//                    Booking charlieBooking = bookingService.bookSeats("FL1", user2.getId(), List.of("1A", "1B", "1D"));
//                    System.out.println("charlie booked seats: " + charlieBooking.getSeats());
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                } catch (SeatAlreadyBookedException e) {
//                    System.out.println("Charlie failed to book seats: " + e.getMessage());
//                }
//            });
//
//            aliceThread.start();
//            charlieThread.start();
//
//            // 4. FIRE THE STARTING GUN! Both users call bookRide simultaneously.
//            startingGun.countDown();
//
//            // Wait for the main thread to ensure the async methods have time to finish
//            System.out.println("Race condition triggered! Waiting for background threads to resolve...");
//            System.out.println("Alice and Charlie both tried to book seats 1A and 1B on flight FL1 at the same time. Only one of them should succeed, while the other should receive a SeatAlreadyBookedException.");
//            System.out.println("Alice Booking: " + aliceThread.getState());
//            System.out.println("Charlie Booking: " + charlieThread.getState());
//        }

        public static void main(String[] args) throws InterruptedException {
            System.out.println("--- Initializing Ticket Booking System ---");

            // 1. Setup Dummy Data
            List<Flight> flights = new ArrayList<>();
            Flight flight = new Flight();
            flight.setFlightNumber("FL1");
            List<Seat> seats = new ArrayList<>();

            // Create 20 seats for FL1
            for (int j = 1; j <= 20; j++) {
                Seat seat = new Seat();
                seat.setFlightId("FL1");
                seat.setSeatId("S" + j);
                seat.setSeatNumber("S" + j);
                seat.setStatus(SeatStatus.IS_AVAILABLE);
                seat.setPrice(1000.0);
                seats.add(seat);
            }
            flight.setAllSeats(seats);
            flights.add(flight);

            FlightService flightService = new FlightService(flights);

            // Custom PaymentService that deterministically fails for test purposes
            PaymentService mockPaymentService = new PaymentService() {
                @Override
                public boolean processPayment(String bookingId, double amount) {
                    if (bookingId.contains("FAIL_ME")) {
                        System.out.println("[Payment Gateway] Payment FAILED for amount: $" + amount);
                        return false;
                    }
                    return true;
                }
            };

            BookingService bookingService = new BookingService(flightService, mockPaymentService);

            // 2. Run Tests Sequentially
            runPartialRollbackTest(bookingService, flightService);
            runDeadlockTest(bookingService);
            runOverbookingLoadTest(bookingService);
            runPaymentFailureEdgeCase(bookingService, mockPaymentService);

            System.out.println("\n--- All Tests Completed ---");
            System.exit(0);
        }

        private static void runPartialRollbackTest(BookingService bookingService, FlightService flightService) {
            System.out.println("\n=== TEST 1: Partial Rollback ===");
            try {
                // Setup: Pre-book S3 so it's unavailable
                bookingService.bookSeats("FL1", "Admin", List.of("S3"));
                System.out.println("Setup: S3 is now pre-booked.");

                // Action: Try to book S1, S2, and S3
                System.out.println("Alice attempting to book S1, S2, S3...");
                bookingService.bookSeats("FL1", "Alice", List.of("S1", "S2", "S3"));

            } catch (SeatAlreadyBookedException e) {
                System.out.println("Caught expected exception: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Caught unexpected exception: " + e.getMessage());
            }

            // Assertion: Verify S1 and S2 were NOT left locked/booked
            List<Seat> flightSeats = flightService.getSeatsForFlight("FL1");
            boolean s1Available = flightSeats.stream().anyMatch(s -> s.getSeatId().equals("S1") && s.getStatus() == SeatStatus.IS_AVAILABLE);
            boolean s2Available = flightSeats.stream().anyMatch(s -> s.getSeatId().equals("S2") && s.getStatus() == SeatStatus.IS_AVAILABLE);

            if (s1Available && s2Available) {
                System.out.println("✅ PASS: Partial rollback successful. S1 and S2 remained available.");
            } else {
                System.err.println("❌ FAIL: S1 or S2 were corrupted and left locked!");
            }
        }

        private static void runDeadlockTest(BookingService bookingService) throws InterruptedException {
            System.out.println("\n=== TEST 2: Deadlock Avoidance ===");

            CountDownLatch startGun = new CountDownLatch(1);
            CountDownLatch doneSignal = new CountDownLatch(2);

            Thread t1 = new Thread(() -> {
                try {
                    startGun.await();
                    bookingService.bookSeats("FL1", "Bob", List.of("S4", "S5", "S6"));
                } catch (Exception e) {
                    System.out.println("Bob failed to book: " + e.getMessage());
                } finally {
                    doneSignal.countDown();
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    startGun.await();
                    // Charlie requests the exact same seats, but in REVERSE order
                    bookingService.bookSeats("FL1", "Charlie", List.of("S6", "S5", "S4"));
                } catch (Exception e) {
                    System.out.println("Charlie failed to book: " + e.getMessage());
                } finally {
                    doneSignal.countDown();
                }
            });

            t1.start();
            t2.start();

            startGun.countDown(); // Fire!

            // If the sorting logic in bookSeats is missing, this await() will hang forever (Deadlock)
            boolean finished = doneSignal.await(5, TimeUnit.SECONDS);

            if (finished) {
                System.out.println("✅ PASS: Threads completed without deadlocking.");
            } else {
                System.err.println("❌ FAIL: System deadlocked! Threads are stuck waiting for locks.");
            }
        }

        private static void runOverbookingLoadTest(BookingService bookingService) throws InterruptedException {
            System.out.println("\n=== TEST 3: Overbooking Load Test (100 Threads) ===");

            int threadCount = 100;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch startGun = new CountDownLatch(1);
            CountDownLatch doneSignal = new CountDownLatch(threadCount);

            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failCount = new AtomicInteger(0);

            for (int i = 0; i < threadCount; i++) {
                String userId = "User_" + i;
                executor.submit(() -> {
                    try {
                        startGun.await();
                        // 100 users fighting for the exact same seat (S10)
                        bookingService.bookSeats("FL1", userId, List.of("S10"));
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        failCount.incrementAndGet();
                    } finally {
                        doneSignal.countDown();
                    }
                });
            }

            startGun.countDown(); // Fire!
            doneSignal.await(); // Wait for all 100 threads
            executor.shutdown();

            System.out.println("Successful bookings: " + successCount.get());
            System.out.println("Failed bookings: " + failCount.get());

            if (successCount.get() == 1 && failCount.get() == 99) {
                System.out.println("✅ PASS: Exactly one user got the seat. 99 were rejected.");
            } else {
                System.err.println("❌ FAIL: Concurrency breach! Double booking occurred.");
            }
        }

        private static void runPaymentFailureEdgeCase(BookingService bookingService, PaymentService paymentService) {
            System.out.println("\n=== TEST 4: Payment Failure Compensation ===");

            try {
                // 1. Successfully reserve seats
                System.out.println("Reserving S11 and S12...");
                Booking newBooking = bookingService.bookSeats("FL1", "Dave", List.of("S11", "S12"));

                // Force a payment failure by manipulating the ID for our mock
                newBooking.setBookingId("FAIL_ME_123");

                // 2. Process Payment
                boolean paymentSuccess = paymentService.processPayment(newBooking.getBookingId(), newBooking.getAmount());

                if (!paymentSuccess) {
                    System.out.println("Payment failed! Initiating compensating transaction...");

                    // 3. The Compensating Transaction (Releasing the seats)
                    newBooking.setStatus(BookingStatus.PAYMENT_FAILED);

                    for (Seat seat : newBooking.getSeats()) {
                        // We must acquire locks again to safely alter state back to available
                        if (seat.getLock().tryLock()) {
                            try {
                                seat.setStatus(SeatStatus.IS_AVAILABLE);
                                System.out.println("Released seat: " + seat.getSeatId());
                            } finally {
                                seat.getLock().unlock();
                            }
                        }
                    }
                    System.out.println("✅ PASS: Payment failed, booking cancelled, and seats returned to inventory.");
                }

            } catch (Exception e) {
                System.err.println("❌ FAIL: Unexpected error during payment test: " + e.getMessage());
            }
        }
    }