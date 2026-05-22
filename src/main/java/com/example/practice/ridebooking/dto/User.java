package com.example.practice.ridebooking.dto;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Set;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private Pair<Integer,Integer> cordinates;
    private Set<String> preferredDriverIds;
}
