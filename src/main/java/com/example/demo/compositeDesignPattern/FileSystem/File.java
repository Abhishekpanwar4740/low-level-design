package com.example.demo.compositeDesignPattern.FileSystem;

public class File implements FileSystem{
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
    @Override
    public void ls() {
        System.out.println("File name is " + name);
    }
}
