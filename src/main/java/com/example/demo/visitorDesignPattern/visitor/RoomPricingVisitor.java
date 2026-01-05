package com.example.demo.visitorDesignPattern.visitor;

import com.example.demo.visitorDesignPattern.element.DeluxeRoom;
import com.example.demo.visitorDesignPattern.element.DoubleRoom;
import com.example.demo.visitorDesignPattern.element.SingleRoom;

public class RoomPricingVisitor implements RoomVisitor{
    @Override
    public void visit(SingleRoom singleRoom) {
        System.out.println("Pricing computation logic for single room");
        singleRoom.roomPrice=1000;
    }

    @Override
    public void visit(DoubleRoom doubleRoom) {
        System.out.println("Pricing computation logic for double room");
        doubleRoom.roomPrice=2000;
    }

    @Override
    public void visit(DeluxeRoom deluxeRoom) {
        System.out.println("Pricing computation logic for deluxe room");
        deluxeRoom.roomPrice=3000;
    }
}
