package com.codeki.tickets.dto;

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

    // Dto para el FeignClient
    private Long id;
    private String origin;
    private String destiny;
    private LocalDateTime departureTime;
    private LocalDateTime arrivingTime;
    private Double convertedPrice;
    private String frequency;
    private CompanyDto company;

}
