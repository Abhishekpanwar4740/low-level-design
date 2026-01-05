package com.example.demo.mediatorDesignPattern;

public class Main {
    public static void main(String[] args) {
        AuctionMediator auctionMediator = new Auction();
        Colleague bidder1 = new Bidder("A", auctionMediator);
        Colleague bidder2 = new Bidder("B", auctionMediator);

        bidder2.placeBid(1000);
        bidder1.placeBid(3000);
        bidder2.placeBid(4000);
    }
}
