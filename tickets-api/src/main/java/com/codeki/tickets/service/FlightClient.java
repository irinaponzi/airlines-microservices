package com.codeki.tickets.service;

import com.codeki.tickets.models.Flight;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "flight-api")
public interface FlightClient {

    @GetMapping("/flights")
    ResponseEntity<List<Flight>> getAllFlights();

    @GetMapping("/{id}")
    ResponseEntity<Flight> getFlightById(@PathVariable Long id);

}
