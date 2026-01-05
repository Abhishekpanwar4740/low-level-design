package com.example.demo.compositeDesignPattern.ProblemStatement;

public class File {
    String name;

    public File(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void ls() {
        System.out.println("File name is " + name);
    }
}
