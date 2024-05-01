package com.codeki.tickets.controller;

import com.codeki.tickets.models.Ticket;
import com.codeki.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/addTicket")
    public ResponseEntity<Ticket> addTicket(@RequestParam Long idFlight, @RequestBody Ticket newTicket) {
        return new ResponseEntity<>(ticketService.createTicket(idFlight, newTicket), HttpStatus.CREATED);
    }

  @PostMapping("/add")
    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket newTicket) {
        return new ResponseEntity<>(ticketService.create(newTicket), HttpStatus.CREATED);
    }

}
