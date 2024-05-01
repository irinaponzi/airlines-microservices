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
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name= "id_flight")
    private Flight flight;

    // Constructor sin ID
    public Ticket(String passengerName, String passengerEmail, String passengerPassport, Flight flight) {
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.passengerPassport = passengerPassport;
        this.flight = flight;
    }

    public Ticket(String passengerName, String passengerEmail, String passengerPassport) {
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.passengerPassport = passengerPassport;

    }
}
