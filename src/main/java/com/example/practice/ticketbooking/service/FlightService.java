package com.example.practice.ticketbooking.service;

import com.example.practice.ticketbooking.dto.Flight;
import com.example.practice.ticketbooking.dto.Seat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FlightService {
    List<Flight> availableFlights;

    public FlightService(List<Flight> flights) {
        // Initialize with some dummy flights
        availableFlights = flights;
    }

    public List<Flight> searchFlights(String departure, String arrival, Date startdatetime, Date enddatetime) {
        // Search for flights based on criteria
        return availableFlights.stream().filter(flight ->
                flight.getDeparture().equalsIgnoreCase(departure) &&
                        flight.getArrival().equalsIgnoreCase(arrival) &&
                        flight.getDepartureTime().after(startdatetime) &&
                        flight.getDepartureTime().before(enddatetime)).toList();
    }
    public List<Seat> getSeatsForFlight(String flightNumber) {
        // Return available seats for a given flight
        return availableFlights.stream().filter(flight->flight.getFlightNumber().equals(flightNumber)).findFirst().map(Flight::getAllSeats).orElse(new ArrayList<>());
    }
}
