package com.example.demo.proxyDesignPattern;

public class ProxyDesignPattern {
    public static void main(String[] args) {
        try {
            EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
            EmployeeDao empTableObj = new EmployeeDaoProxy(employeeDao);
            empTableObj.create("ADMIN", new EmplayeeDo());
            System.out.println("Operation successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
