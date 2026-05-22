package com.example.practice.ridebooking.service;

import com.example.practice.ridebooking.dto.Driver;
import com.example.practice.ridebooking.dto.User;
import com.example.practice.ridebooking.exception.DriverNotFoundException;
import org.springframework.data.util.Pair;

import java.util.List;

public class MostExperiencedDriverStrategy implements FindDriverStrategy{
    @Override
    public List<Driver> findDriver(Pair<Integer, Integer> source, Pair<Integer, Integer> destination, List<Driver> availableDrivers, User user,Boolean checkPreference) throws DriverNotFoundException {
        if(availableDrivers.isEmpty()){
            throw new DriverNotFoundException("No drivers available");
        }
        availableDrivers.sort((d1, d2) -> Long.compare(d2.getExperienceInDays(), d1.getExperienceInDays()));
        if(checkPreference)
            availableDrivers.sort((d1, d2) -> {
                boolean isD1Preferred = checkPreferredDriver(d1, user, prefferedDistance);
                boolean isD2Preferred = checkPreferredDriver(d2, user, prefferedDistance);

                if (isD1Preferred && !isD2Preferred) return -1; // d1 moves up
                if (!isD1Preferred && isD2Preferred) return 1;  // d2 moves up
                return 0;
            });
        return availableDrivers;
    }
}
