package com.codeki.tickets.service;

import com.codeki.tickets.models.Flight;
import com.codeki.tickets.models.Ticket;
import com.codeki.tickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    FlightClient flightClient;


    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket createTicket(Long idFlight, Ticket newTicket) {
        ResponseEntity<Flight> response = flightClient.getFlightById(idFlight);
        if (response.getStatusCode().is2xxSuccessful()) {
            Flight flight = response.getBody();
            newTicket.setFlight(flight);
            return ticketRepository.save(newTicket);
        }
        return null;
    }

       public Ticket create(Ticket newTicket) {

            return ticketRepository.save(newTicket);
        }



}
