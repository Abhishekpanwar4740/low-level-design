package com.example.demo.proxyDesignPattern;

public class EmployeeDaoImpl implements EmployeeDao{

    @Override
    public void create(String client, EmplayeeDo obj) throws Exception{
        System.out.println("created new row in the employee table successfully");
    }

    @Override
    public void delete(String client, int employeeId) throws Exception{
        System.out.println("deleted row in the employee table successfully");
    }

    @Override
    public void get(String client, int employeeId) throws Exception{
        System.out.println("found row in the employee table successfully");
    }
}
