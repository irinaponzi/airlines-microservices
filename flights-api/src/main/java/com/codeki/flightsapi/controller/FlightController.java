package com.codeki.flightsapi.controller;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.model.Flight;
import com.codeki.flightsapi.service.FlightService;
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

    // Obtener todos los vuelos
    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        return new ResponseEntity<>(flightService.findAll(), HttpStatus.OK);
    }

    // Buscar vuelo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.findById(id), HttpStatus.OK);
    }

    // Buscar vuelos por origen
    @CrossOrigin
    @GetMapping("/origin")
    public ResponseEntity<List<FlightDto>> getFlightsByOrigin(@RequestParam String origin) {
        return new ResponseEntity<>(flightService.findByOrigin(origin), HttpStatus.OK);
    }

    // Buscar vuelos por origen y destino
    @CrossOrigin
    @GetMapping("/location")
    public ResponseEntity<List<FlightDto>> getFlightsByLocations(@RequestParam String origin, @RequestParam String destiny) {
        return new ResponseEntity<>(flightService.findByOriginAndDestiny(origin, destiny), HttpStatus.OK);
    }

    // Buscar vuelos por compañía
    @CrossOrigin
    @GetMapping("/company")
    public ResponseEntity<List<FlightDto>> getFlightsByCompany(@RequestParam String companyName) {
        return new ResponseEntity<>(flightService.findByCompanyName(companyName), HttpStatus.OK);
    }

    // Buscar vuelos por ofertas, según el precio
    @CrossOrigin
    @GetMapping("/offers")
    public ResponseEntity<List<FlightDto>> getOffers(@RequestParam Integer offerPrice) {
        return new ResponseEntity<>(flightService.getOffers(offerPrice), HttpStatus.OK);
    }

    // Crear vuelo
    @PostMapping("/create/{companyId}")
    public ResponseEntity<Flight> createFlight(@PathVariable Long companyId, @RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.create(companyId, flight), HttpStatus.CREATED);
    }

    // Actualizar vuelo por ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.update(id, flight), HttpStatus.OK);
    }

    // Eliminar vuelo por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteFlight(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.deleteById(id), HttpStatus.OK);
    }
}
