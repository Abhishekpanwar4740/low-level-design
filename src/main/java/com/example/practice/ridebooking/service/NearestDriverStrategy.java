package com.example.practice.ridebooking.service;

import com.example.practice.ridebooking.dto.Driver;
import com.example.practice.ridebooking.dto.User;
import com.example.practice.ridebooking.exception.DriverNotFoundException;
import org.springframework.data.util.Pair;

import java.util.List;

public class NearestDriverStrategy implements FindDriverStrategy {
    @Override
    public List<Driver> findDriver(Pair<Integer, Integer> source, Pair<Integer, Integer> destination, List<Driver> availableDrivers, User user, Boolean checkPreference) throws DriverNotFoundException {
        if (availableDrivers.isEmpty()) {
            throw new DriverNotFoundException("No drivers available");
        }
        availableDrivers.sort((d1, d2) -> Double.compare(getDistance(d1, source), getDistance(d2, source)));
        if (checkPreference)
            availableDrivers.sort((d1, d2) -> {
                boolean isD1Preferred = checkPreferredDriver(d1, user, prefferedDistance);
                boolean isD2Preferred = checkPreferredDriver(d2, user, prefferedDistance);

                if (isD1Preferred && !isD2Preferred) return -1;
                if (!isD1Preferred && isD2Preferred) return 1;
                return 0;
            });
        return availableDrivers;
    }

    public Double getDistance(Driver driver, Pair<Integer, Integer> source) {
        return Math.sqrt(Math.pow(driver.getCordinates().getFirst() - source.getFirst(), 2) + Math.pow(driver.getCordinates().getSecond() - source.getSecond(), 2));
    }
}
