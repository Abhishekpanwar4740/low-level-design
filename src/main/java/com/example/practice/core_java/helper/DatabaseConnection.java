package com.example.practice.core_java.helper;

public class DatabaseConnection{
    private static volatile DatabaseConnection instance;
    public DatabaseConnection(){
    }
    public static DatabaseConnection getInstance(){
        if(instance==null){
            synchronized (DatabaseConnection.class){
                if(instance==null){
                    instance=new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}
