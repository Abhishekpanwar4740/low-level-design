package com.example.practice.core_java;

import com.example.practice.core_java.helper.DatabaseConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Application {
    public static void main(String[] args){
        /*
        * Sorting a map by its values using stream API*/
//        Map<String,Integer> m1=new HashMap<>();
//        m1.put("Alice",10);
//        m1.put("Bob",30);
//        m1.put("Charlie",20);
//        m1.put("Frank",19);
//        m1.put("Granny",8);
//        List<Map.Entry<String,Integer>> m2=m1.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue()).toList();
//        System.out.println(m2.toString());

/*
* Singleton pattern implementation using double checked locking*/
        DatabaseConnection s1=DatabaseConnection.getInstance();
        DatabaseConnection s2=DatabaseConnection.getInstance();
        DatabaseConnection s3=new DatabaseConnection();
        System.out.println(s1.hashCode()==s2.hashCode());
        System.out.println(s1.hashCode()==s3.hashCode());
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        System.out.println(s3.hashCode());
    }

}
