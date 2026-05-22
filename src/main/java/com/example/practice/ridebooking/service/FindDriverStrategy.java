package com.example.practice.ridebooking.service;

import com.example.practice.ridebooking.dto.Driver;
import com.example.practice.ridebooking.dto.User;
import com.example.practice.ridebooking.exception.DriverNotFoundException;
import org.springframework.data.util.Pair;

import java.util.List;

public interface FindDriverStrategy {
    Long prefferedDistance = 50L;
    List<Driver> findDriver(Pair<Integer, Integer> source, Pair<Integer, Integer> destination, List<Driver> availableDrivers, User user,Boolean checkPreference) throws DriverNotFoundException;
    default Boolean checkPreferredDriver(Driver driver, User user,Long distance){
        double dist = Math.sqrt(Math.pow(driver.getCordinates().getFirst()-user.getCordinates().getFirst(),2)+Math.pow(driver.getCordinates().getSecond()-user.getCordinates().getSecond(),2));
        return user.getPreferredDriverIds().contains(driver.getId()) && dist <= distance;
    }
}
