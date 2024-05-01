package com.codeki.tickets.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String passengerName;
    private String passengerEmail;
    private String passengerPassport;
    private Long idFlight;

    // Constructor sin ID
    public Ticket(String passengerName, String passengerEmail, String passengerPassport, Long idFlight) {
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.passengerPassport = passengerPassport;
        this.idFlight = idFlight;
    }
}
