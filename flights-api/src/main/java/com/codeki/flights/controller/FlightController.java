package com.codeki.flights.controller;

import com.codeki.flights.dto.FlightDto;
import com.codeki.flights.dto.ResponseDto;
import com.codeki.flights.model.Flight;
import com.codeki.flights.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        return new ResponseEntity<>(flightService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.findById(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/origin")
    public ResponseEntity<List<FlightDto>> getFlightsByOrigin(@RequestParam String origin) {
        return new ResponseEntity<>(flightService.findByOrigin(origin), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/location")
    public ResponseEntity<List<FlightDto>> getFlightsByLocations(@RequestParam String origin, @RequestParam String destiny) {
        return new ResponseEntity<>(flightService.findByOriginAndDestiny(origin, destiny), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/company")
    public ResponseEntity<List<FlightDto>> getFlightsByCompany(@RequestParam String companyName) {
        return new ResponseEntity<>(flightService.findByCompanyName(companyName), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/offers")
    public ResponseEntity<List<FlightDto>> getOffers(@RequestParam Integer offerPrice) {
        return new ResponseEntity<>(flightService.getOffers(offerPrice), HttpStatus.OK);
    }

    @PostMapping("/create/{companyId}")
    public ResponseEntity<Flight> createFlight(@PathVariable Long companyId, @RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.create(companyId, flight), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.update(id, flight), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteFlight(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.deleteById(id), HttpStatus.OK);
    }
}
