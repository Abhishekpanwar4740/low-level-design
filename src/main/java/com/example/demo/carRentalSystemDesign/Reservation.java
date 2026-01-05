package com.example.demo.carRentalSystemDesign;

import com.example.demo.carRentalSystemDesign.product.Vehicle;

import java.util.Date;

public class Reservation {
    int resId;
    User user;
    Vehicle vehicle ;
    Date dateOfBooking;
    Date dateBookedFrom;
    Date dateBookedTo;
    Long fromTimeStamp;
    Long toTimeStamp;
    Long pickupLocation;
    Long dropLocation;
    ReservationType reservationType;
    ReservationStatus reservationStatus;
    Location location;

    public int createReserve(User user,Vehicle vehicle){
        resId=453456;
        this.user=user;
        this.vehicle=vehicle;
        reservationType=ReservationType.DAILY;
        reservationStatus=ReservationStatus.SCHEDULED;

        return resId;
    }
    //CRUD Operations
}
