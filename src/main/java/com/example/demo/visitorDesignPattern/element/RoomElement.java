package com.example.demo.visitorDesignPattern.element;

import com.example.demo.visitorDesignPattern.visitor.RoomVisitor;

public interface RoomElement {
    void accept(RoomVisitor visitor);
}
