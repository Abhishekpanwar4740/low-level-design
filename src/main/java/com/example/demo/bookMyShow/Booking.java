package com.example.demo.bookMyShow;

import org.springframework.web.bind.support.SessionAttributeStore;

import java.util.List;

public class Booking {
    Show show;
    List<Seat> seatList;
    Payment payment;


    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
