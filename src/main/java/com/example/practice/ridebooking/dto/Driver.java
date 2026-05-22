package com.example.practice.ridebooking.dto;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.concurrent.locks.ReentrantLock;

@Data
public class Driver {
    private String id;
    private String name;
    private String email;
    private StatusEnum status;
    private VehicleDetails vehicleDetails;
    private Pair<Integer,Integer> cordinates;
    private Long experienceInDays;
    private final ReentrantLock lock = new ReentrantLock();
}
