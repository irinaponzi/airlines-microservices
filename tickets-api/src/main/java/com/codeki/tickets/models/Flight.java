package com.codeki.tickets.models;

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
    @Column(name = "id_flight")
    private Long id;
    private String origin;
    private String destiny;
    private LocalDateTime departureTime;
    private LocalDateTime arrivingTime;
    private Double convertedPrice;
    private String frequency;
   // private Company company;

    // Constructor sin ID
    public Flight(String origin, String destiny, LocalDateTime departureTime, LocalDateTime arrivingTime, Double convertedPrice, String frequency) {
        this.origin = origin;
        this.destiny = destiny;
        this.departureTime = departureTime;
        this.arrivingTime = arrivingTime;
        this.convertedPrice = convertedPrice;
        this.frequency = frequency;
       // this.company = company;
    }


}
