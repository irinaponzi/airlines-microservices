package com.codeki.tickets.controller;

import com.codeki.tickets.models.Flight;
import com.codeki.tickets.service.FlightClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {

    @Autowired
    FlightClient flightClient;

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return flightClient.getAllFlights();
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightClient.getFlightById(id);
    }

}
