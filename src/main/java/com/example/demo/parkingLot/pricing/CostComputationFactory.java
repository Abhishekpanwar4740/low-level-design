package com.example.demo.parkingLot.pricing;

public class CostComputationFactory {
    public CostComputation getCostComputation(String vehicleType,PricingStrategy pricingStrategy){
        return switch (vehicleType) {
            case "TwoWheeler" -> new TwoWheelerCC(pricingStrategy);
            case "FourWheeler" -> new FourWheelerCC(pricingStrategy);
            default -> null;
        };
    }
}
