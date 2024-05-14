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
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime purchaseDate;
    private Long idUser;
    private Long idFlight;

    // Constructor sin ID
    public Ticket(Long idUser, Long idFlight) {
        this.idUser = idUser;
        this.idFlight = idFlight;
    }
}
