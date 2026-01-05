package com.example.demo.visitorDesignPattern.visitor;

import com.example.demo.visitorDesignPattern.element.DeluxeRoom;
import com.example.demo.visitorDesignPattern.element.DoubleRoom;
import com.example.demo.visitorDesignPattern.element.SingleRoom;

public interface RoomVisitor {
    void visit(SingleRoom singleRoom);
    void visit(DoubleRoom doubleRoom);
    void visit(DeluxeRoom deluxeRoom);
}
