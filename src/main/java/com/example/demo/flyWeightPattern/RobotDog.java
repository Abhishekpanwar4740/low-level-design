package com.example.demo.flyWeightPattern;

public class RobotDog implements IRobot{
    public String type;
    private Sprites body;

    public RobotDog(String type, Sprites body) {
        this.type = type;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public Sprites getBody() {
        return body;
    }

    @Override
    public void display(int x, int y) {

    }
}
