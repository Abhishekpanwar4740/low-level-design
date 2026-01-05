package com.example.demo.visitorDesignPattern;

import com.example.demo.visitorDesignPattern.element.DeluxeRoom;
import com.example.demo.visitorDesignPattern.element.DoubleRoom;
import com.example.demo.visitorDesignPattern.element.RoomElement;
import com.example.demo.visitorDesignPattern.element.SingleRoom;
import com.example.demo.visitorDesignPattern.visitor.RoomMaintainanceVisitor;
import com.example.demo.visitorDesignPattern.visitor.RoomPricingVisitor;
import com.example.demo.visitorDesignPattern.visitor.RoomVisitor;

public class Main {
    public static void main(String[] args){
        RoomElement singleRoomObj=new SingleRoom();
        RoomElement doubleRoomObj=new DoubleRoom();
        RoomElement deluxeRoomObj=new DeluxeRoom();

        RoomVisitor pricingVisitor=new RoomPricingVisitor();
        singleRoomObj.accept(pricingVisitor);
        System.out.println(((SingleRoom)singleRoomObj).roomPrice);
        doubleRoomObj.accept(pricingVisitor);
        System.out.println(((DoubleRoom)doubleRoomObj).roomPrice);
        deluxeRoomObj.accept(pricingVisitor);
        System.out.println(((DeluxeRoom)deluxeRoomObj).roomPrice);

        RoomVisitor maintainanceVisitorObj=new RoomMaintainanceVisitor();
        singleRoomObj.accept(maintainanceVisitorObj);
        doubleRoomObj.accept(maintainanceVisitorObj);
        deluxeRoomObj.accept(maintainanceVisitorObj);

    }
}
