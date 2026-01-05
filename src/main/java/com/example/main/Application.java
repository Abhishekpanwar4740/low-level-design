package com.example.main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Application {
    public static void main(String args[]) throws InterruptedException {
//        Map<Integer, String> map = new HashMap<>();
//
//        Runnable task = () -> {
//            for (int i = 0; i < 1000; i++) {
//                map.put(i, "Value" + i);
//            }
//        };
//
//        Thread t1 = new Thread(task);
//        Thread t2 = new Thread(task);
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//
//        System.out.println("Final size HashMap: " + map.size());
//
//        Map<Integer, String> map1 = new ConcurrentHashMap<>();
//
//        Runnable task1 = () -> {
//            for (int i = 0; i < 1000; i++) {
//                map1.put(i, "Value" + i);
//            }
//        };
//
//        Thread t3 = new Thread(task1);
//        Thread t4 = new Thread(task1);
//
//        t3.start();
//        t4.start();
//        t3.join();
//        t4.join();
//
//        System.out.println("Final size Concurrent Map: " + map1.size());

//        List list=new ArrayList<>();
//        list.add(1);
//        list.add("Abhishek");
//        list.forEach(a->System.out.println(Long.parseLong(a.toString())*2));
//        List<Integer> integers=new CopyOnWriteArrayList<>();
//        integers.add(1);
//        integers.add(2);
//        Iterator<Integer> i=integers.iterator();
//        while(i.hasNext()){
//            System.out.println(i.next());
//            integers.add(3);
//        }

        Map<Integer,String> map=new HashMap<>();
        map.put(1,"one");
        map.put(223421,"two");
        map.put(4,"four");
        map.put(3,"three");
        Iterator<Integer> iterator=map.keySet().iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println(map);
    }
}
