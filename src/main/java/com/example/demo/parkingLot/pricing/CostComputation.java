package com.example.demo.parkingLot.pricing;

import com.example.demo.parkingLot.Ticket;

public class CostComputation {
    PricingStrategy pricingStrategy;

    public CostComputation(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public long price(Ticket ticket) {
        return pricingStrategy.price(ticket);
    }
}
