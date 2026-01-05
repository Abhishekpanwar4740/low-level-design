package com.example.demo.mediatorDesignPattern;

public interface AuctionMediator {
     void addBidder(Colleague colleague);
     void placeBid(Colleague colleague,int bidAmount);
}
