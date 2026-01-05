package com.example.demo.mediatorDesignPattern;

import java.util.ArrayList;
import java.util.List;

public class Auction implements AuctionMediator {
    List<Colleague> colleagueList = new ArrayList<>();

    @Override
    public void addBidder(Colleague colleague) {
        colleagueList.add(colleague);
    }

    @Override
    public void placeBid(Colleague bidder, int bidAmount) {
        for (Colleague colleague : colleagueList) {
            if (!colleague.getName().equals(bidder.getName())) {
                colleague.receiveBidNotification(bidAmount);
            }
        }

    }
}
