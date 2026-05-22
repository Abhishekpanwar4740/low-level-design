package com.example.practice.ridebooking.dto;

import lombok.Data;
import org.springframework.data.util.Pair;
@Data
public class Ride {
    private String id;
    private String userId;
    private String driverId;
    private Pair<Integer,Integer> sourceCordinates;
    private Pair<Integer,Integer> destinationCordinates;
    private RideStatusEnum status;
}
