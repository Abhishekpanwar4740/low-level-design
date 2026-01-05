package com.example.demo.proxyDesignPattern;

public interface EmployeeDao {
    void create(String client,EmplayeeDo obj) throws Exception;
    void delete(String client,int employeeId) throws Exception;
    void get(String client,int employeeId) throws Exception;
}
