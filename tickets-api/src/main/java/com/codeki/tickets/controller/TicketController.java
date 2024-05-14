package com.codeki.tickets.controller;

import com.codeki.tickets.dto.ResponseDto;
import com.codeki.tickets.models.Ticket;
import com.codeki.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("")
    public ResponseEntity<List<Map<String, Object>>> getAllTickets() {
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/id-flight")
    public ResponseEntity<List<Ticket>> getTicketByIdFlight(@RequestParam Long idFlight) {
        return new ResponseEntity<>(ticketService.findByIdFlight(idFlight), HttpStatus.OK);
    }

    @GetMapping("/id-user")
    public ResponseEntity<List<Ticket>> getTicketByIdUser(@RequestParam Long idUser) {
        return new ResponseEntity<>(ticketService.findByIdUser(idUser), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Ticket> addTicket(@RequestParam Long idUser, @RequestParam Long idFlight) {
        return new ResponseEntity<>(ticketService.createTicket(idUser, idFlight), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteTicket(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.deleteById(id), HttpStatus.OK);
    }

}
