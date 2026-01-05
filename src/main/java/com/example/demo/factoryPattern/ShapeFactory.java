package com.example.demo.factoryPattern;

public class ShapeFactory {
    public Shape getShape(String name) {
        switch (name) {
            case "Circle":
                return new Circle();
            case "Rectangle":
                return new Rectangle();
            case "Square":
                return new Square();
            default:
                return null;
        }
    }
}
