package com.example.practice.ridebooking.service;

import com.example.practice.ridebooking.dto.*;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RideSharingService {
     void registerUser(User userDetails);
     void registerDriver(Driver driverDetails);
     Ride bookRide(User user, Pair<Integer,Integer> destX, Pair<Integer,Integer> destY, FindDriverStrategy strategy);
     void offerRide(String driverId, Pair<Integer,Integer> sourceX, Pair<Integer,Integer> sourceY) throws InterruptedException;
     List<Ride> get_driver_ride_history(String driverId);
     List<Ride> get_user_ride_history(String userId);
}
