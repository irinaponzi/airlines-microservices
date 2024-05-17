package com.codeki.tickets.service;

import com.codeki.tickets.dto.ResponseDto;
import com.codeki.tickets.exceptions.NotFoundException;
import com.codeki.tickets.models.Ticket;
import com.codeki.tickets.repository.TicketRepository;
import com.codeki.tickets.utils.Utils;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    FlightFeignClient flightFeignClient;
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    Utils utils;

    public List<Map<String, Object>> findAll() {
        return utils.getCompleteTicketList(ticketRepository.findAll());
    }

    public List<Ticket> findByIdFlight(Long idFlight) {
        List<Ticket> ticketList = ticketRepository.findByIdFlight(idFlight);
        if (!ticketList.isEmpty()) {
            return ticketList;
        }
        throw new NotFoundException("No results found");
    }

    public List<Ticket> findByIdUser(Long idUser) {
        List<Ticket> ticketList = ticketRepository.findByIdUser(idUser);
        if (!ticketList.isEmpty()) {
            return ticketList;
        }
        throw new NotFoundException("No results found");
    }

    public Ticket createTicket(Long idUser, Long idFlight) {
        try {
            //Solo se llama a los métodos, porque en caso de no encontrar el vuelo y/o usuario se lanzará la excepción
            flightFeignClient.getFlightById(idFlight);
            userFeignClient.getUserById(idUser);

            Ticket newTicket = new Ticket();
            LocalDateTime purchaseDate = LocalDateTime.now();

            newTicket.setPurchaseDate(purchaseDate);
            newTicket.setIdFlight(idFlight);
            newTicket.setIdUser(idUser);
            return ticketRepository.save(newTicket);

        } catch (FeignException e) {
            throw new NotFoundException("Unable to create: Flight or User not found");
        }
    }

    public ResponseDto deleteById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if(ticketOptional.isPresent()) {
            ticketRepository.deleteById(id);
            return new ResponseDto("The ticket " + id + " has been deleted");
        }
        throw new NotFoundException("Unable to delete: Ticket not found");
    }
}
