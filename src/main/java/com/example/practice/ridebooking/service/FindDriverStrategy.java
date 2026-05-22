package com.example.practice.ridebooking.service;

import com.example.practice.ridebooking.dto.Driver;
import org.springframework.data.util.Pair;

public interface FindDriverrStrategy {
    Driver findDriver(Pair<Integer, Integer> source, Pair<Integer, Integer> destination);
}
