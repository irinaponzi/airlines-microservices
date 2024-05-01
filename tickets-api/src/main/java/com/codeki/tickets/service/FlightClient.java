package com.codeki.tickets.service;

import com.codeki.tickets.dto.FlightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "flights-api")
public interface FlightClient {

    @GetMapping("/flights")
    ResponseEntity<List<FlightDto>> getAllFlights();

    @GetMapping("/flights/{id}")
    ResponseEntity<FlightDto> getFlightById(@PathVariable Long id);

    @GetMapping("/flights/origin")
    ResponseEntity<List<FlightDto>> getFlightsByOrigin(@RequestParam String origin);

    @GetMapping("/flights/location")
    ResponseEntity<List<FlightDto>> getFlightsByLocations(@RequestParam String origin, @RequestParam String destiny);

    @GetMapping("/flights/company")
    ResponseEntity<List<FlightDto>> getFlightsByCompany(@RequestParam String companyName);

    @GetMapping("/flights/offers")
    ResponseEntity<List<FlightDto>> getOffers(@RequestParam Integer offerPrice);

}
