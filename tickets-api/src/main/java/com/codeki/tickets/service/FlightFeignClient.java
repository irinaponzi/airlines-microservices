package com.codeki.tickets.service;

import com.codeki.tickets.dto.FlightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "flights-api")
public interface FlightFeignClient {

    @GetMapping("/flights")
    ResponseEntity<List<FlightDto>> getAllFlights();

    @GetMapping("/flights/{id}")
    ResponseEntity<FlightDto> getFlightById(@PathVariable Long id);

}
