package com.codeki.flightsapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destiny;
    private LocalDateTime departureTime;
    private LocalDateTime arrivingTime;
    private Double price;
    private String frequency;
    @ManyToOne
    @JoinColumn(name= "company_id")
    Company company;

    // Constructor sin ID
    public Flight(String origin, String destiny, LocalDateTime departureTime, LocalDateTime arrivingTime, Double price, String frequency, Company company) {
        this.origin = origin;
        this.destiny = destiny;
        this.departureTime = departureTime;
        this.arrivingTime = arrivingTime;
        this.price = price;
        this.frequency = frequency;
        this.company = company;
    }
}
