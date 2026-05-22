package com.example.practice.ridebooking.service;

import com.example.practice.ridebooking.dto.*;
import com.example.practice.ridebooking.exception.DriverNotFoundException;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class RideSharingServiceImpl implements RideSharingService {
    private List<Driver> availableDrivers;
    private List<Driver> bookedDrivers;
    private List<Ride> rides;
    private List<User> registeredUsers;
    private List<Driver> registeredDrivers;
    private ExecutorService executorService;

    public RideSharingServiceImpl() {
        this.availableDrivers = new CopyOnWriteArrayList<>();
        this.bookedDrivers = new CopyOnWriteArrayList<>();
        this.rides = new ArrayList<>();
        this.registeredDrivers = new CopyOnWriteArrayList<>();
        this.registeredUsers = new CopyOnWriteArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void registerUser(User userDetails) {
        UUID id = UUID.randomUUID();
        userDetails.setId(id.toString());
        registeredUsers.add(userDetails);
    }

    @Override
    public void registerDriver(Driver driverDetails) {
        UUID id = UUID.randomUUID();
        driverDetails.setId(id.toString());
        registeredDrivers.add(driverDetails);
        availableDrivers.add(driverDetails);
    }

    @Override
    public Ride bookRide(User user, Pair<Integer, Integer> source, Pair<Integer, Integer> dest, FindDriverStrategy strategy) {
        Ride ride = new Ride();
        ride.setUserId(user.getId());
        ride.setSourceCordinates(source);
        ride.setDestinationCordinates(dest);
        ride.setStatus(RideStatusEnum.ASSIGNING_DRIVER);
        CompletableFuture.runAsync(() -> {
            assignDriverAsync(user, source, dest, strategy, ride);
        }, executorService);
        return ride;
    }

    private void assignDriverAsync(User user, Pair<Integer, Integer> source, Pair<Integer, Integer> dest, FindDriverStrategy strategy, Ride ride) {
        try {
            List<Driver> drivers = strategy.findDriver(source, dest, availableDrivers, user, true);
            for (Driver driver : drivers) {
                if (driver.getLock().tryLock()) {
                    try {
                        if (askDriverWithTimeout(source, dest, ride, driver)) {
                            bookedDrivers.add(driver);
                            availableDrivers.remove(driver);
                            ride.setDriverId(driver.getId());
                            ride.setStatus(RideStatusEnum.DRIVER_ASSIGNED);
                            rides.add(ride);
                            break;
                        }
                    } finally {
                        driver.getLock().unlock();
                    }
                }
            }
        } catch (DriverNotFoundException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean askDriverWithTimeout(Pair<Integer, Integer> source, Pair<Integer, Integer> dest, Ride ride, Driver driver) throws InterruptedException {
        System.out.println("Notifying driver with " + driver.getId() + " for ride from " + source + " to " + dest);
        offerRide(driver.getId(), source, dest);
        Callable<Boolean> driverResponseTask = this::simulateRideCompletion;
        Future<Boolean> driverResponse = executorService.submit(driverResponseTask);
        try {
            Boolean isAccepted = driverResponse.get(10, TimeUnit.SECONDS);
            if (isAccepted) {
                System.out.println("Driver " + driver.getName() + " accepted the ride.");
                return true;
            } else {
                System.out.println("Driver " + driver.getName() + " rejected the ride.");
            }
        } catch (TimeoutException | ExecutionException e) {
            driverResponse.cancel(true);
            System.out.println("Driver with " + driver.getId() + " TIMED OUT. Moving to next driver..");
        }
        return false;
    }

    private Boolean simulateRideCompletion() throws InterruptedException {
        long responseTime = (long) (Math.random() * 10000) + 1000;
        Thread.sleep(responseTime);
        return Math.random() < 0.99;
    }

    @Override
    public void offerRide(String driverId, Pair<Integer, Integer> sourceX, Pair<Integer, Integer> dest) throws InterruptedException {
        System.out.println("Ride offered to driver with " + driverId + " from " + sourceX + " to " + dest);
    }

    @Override
    public List<Ride> get_user_ride_history(String userId) {
        System.out.println("Ride history for user with " + userId);
        return rides.stream().filter(ride -> ride.getUserId().equals(userId)).toList();
    }

    @Override
    public List<Ride> get_driver_ride_history(String driverId) {
        System.out.println("Ride history for driver with " + driverId);
        return rides.stream().filter(ride -> ride.getDriverId().equals(driverId)).toList();
    }
}
