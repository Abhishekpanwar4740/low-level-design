package com.example.demo.interpretorDesignPattern;

import java.util.HashMap;
import java.util.Map;

public class Context {
    Map<String, Integer> contextMap = new HashMap<>();

    public void put(String stringValue, Integer intValue) {
        contextMap.put(stringValue, intValue);
    }

    public int get(String stringValue) {
        return contextMap.get(stringValue);
    }

}
