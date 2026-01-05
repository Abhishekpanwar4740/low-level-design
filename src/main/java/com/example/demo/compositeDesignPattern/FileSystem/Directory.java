package com.example.demo.compositeDesignPattern.FileSystem;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystem {
    String directoryName;
    List<FileSystem> objectList;

    public Directory(String directoryName) {
        this.directoryName = directoryName;
        this.objectList = new ArrayList<>();
    }

    public void add(FileSystem object) {
        objectList.add(object);
    }

    public void ls() {
        System.out.println("Directory name " + directoryName);
        for (FileSystem fileSystem : objectList) {

            fileSystem.ls();
            ;

        }
    }
}
