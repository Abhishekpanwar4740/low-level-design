package com.example.demo.visitorDesignPattern.visitor;

import com.example.demo.visitorDesignPattern.element.DeluxeRoom;
import com.example.demo.visitorDesignPattern.element.DoubleRoom;
import com.example.demo.visitorDesignPattern.element.SingleRoom;

public class RoomMaintainanceVisitor implements RoomVisitor{
    @Override
    public void visit(SingleRoom singleRoom) {
        System.out.println("Pricing maintainance for single room");
    }

    @Override
    public void visit(DoubleRoom doubleRoom) {
        System.out.println("Pricing maintainance for double room");
    }

    @Override
    public void visit(DeluxeRoom deluxeRoom) {
        System.out.println("Pricing maintainance for deluxe room");
    }
}
