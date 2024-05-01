package com.codeki.flights.dto;

import com.codeki.flights.model.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDto {

    // En FlightDto el precio del vuelo es en pesos
    private Long id;
    private String origin;
    private String destiny;
    private LocalDateTime departureTime;
    private LocalDateTime arrivingTime;
    private Double convertedPrice;
    private String frequency;
    private Company company;

    // Constructor sin ID
    public FlightDto(String origin, String destiny, LocalDateTime departureTime, LocalDateTime arrivingTime, Double convertedPrice, String frequency, Company company) {
        this.origin = origin;
        this.destiny = destiny;
        this.departureTime = departureTime;
        this.arrivingTime = arrivingTime;
        this.convertedPrice = convertedPrice;
        this.frequency = frequency;
        this.company = company;
    }
}
