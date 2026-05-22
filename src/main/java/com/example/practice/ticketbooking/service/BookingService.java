package com.example.practice.ticketbooking.service;

import com.example.practice.ridebooking.dto.Driver;
import com.example.practice.ticketbooking.dto.Booking;
import com.example.practice.ticketbooking.dto.BookingStatus;
import com.example.practice.ticketbooking.dto.Seat;
import com.example.practice.ticketbooking.dto.SeatStatus;
import com.example.practice.ticketbooking.exception.SeatAlreadyBookedException;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BookingService {
    FlightService flightService;
    PaymentService paymentService;

    public BookingService(FlightService flightService, PaymentService paymentService) {
        this.flightService = flightService;
        this.paymentService = paymentService;
    }

    public Booking bookSeats(String flightId, String userId, List<String> seatIds) throws SeatAlreadyBookedException {
        Booking newBooking = new Booking();
        System.out.println("User " + userId + " is trying to book seats: " + seatIds + " on flight " + flightId);
        List<Seat> seats = flightService.getSeatsForFlight(flightId);
        System.out.println("Seats available for flight " + flightId + ": " + seats.size());
        List<Seat> bookingSeats = seats.stream().filter(seat -> seatIds.contains(seat.getSeatId())).sorted(Comparator.comparing(Seat::getSeatId)).toList();
        List<Seat> lockedSeats = new ArrayList<>();
        try {
            for (Seat seat : bookingSeats) {
                boolean acquired = false;
                try {
                    acquired = seat.getLock().tryLock(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted while trying to lock seat " + seat.getSeatId());
                }
                if (acquired) lockedSeats.add(seat);
                else throw new RuntimeException("System busy, could not lock seat: " + seat.getSeatId());
            }
            for (Seat seat : lockedSeats) {
                if (seat.getStatus() == SeatStatus.IS_BOOKED) {
                    throw new SeatAlreadyBookedException("Seat " + seat.getSeatId() + " is already booked.");
                }
            }
            for (Seat seat : lockedSeats) {
                seat.setStatus(SeatStatus.IS_BOOKED);
            }
            newBooking.setFlightId(flightId);
            newBooking.setUserId(userId);
            newBooking.setSeats(bookingSeats);
            newBooking.setAmount(calculateAmount(bookingSeats));
            newBooking.setStatus(BookingStatus.PAYMENT_PENDING);
            return newBooking;
        } finally {
            for (Seat seat : lockedSeats) {
                seat.getLock().unlock();
            }
        }

    }

    private Double calculateAmount(List<Seat> seats) {
        // Implement logic to calculate total amount based on seat types and pricing
        return seats.stream().mapToDouble(Seat::getPrice).sum();
    }

}
