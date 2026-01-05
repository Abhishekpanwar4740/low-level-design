package com.example.demo.proxyDesignPattern;

import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeDaoProxy implements EmployeeDao{
    EmployeeDaoImpl employeeDao;
    @Autowired
    public EmployeeDaoProxy(EmployeeDaoImpl employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void create(String client, EmplayeeDo obj) throws Exception{
        if(client.equals("ADMIN")){
            employeeDao.create(client,obj);
            return;
        }
        throw new Exception("Access Denied");
    }

    @Override
    public void delete(String client, int employeeId) throws Exception{
        if(client.equals("ADMIN")){
            employeeDao.delete(client,employeeId);
            return;
        }
        throw new Exception("Access Denied");
    }

    @Override
    public void get(String client, int employeeId) throws Exception{
        if(client.equals("ADMIN") || client.equals("USER")){
            employeeDao.get(client,employeeId);
            return;
        }
        throw new Exception("Access Denied");
    }
}
