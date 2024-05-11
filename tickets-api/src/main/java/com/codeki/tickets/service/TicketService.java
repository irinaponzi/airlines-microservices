package com.codeki.tickets.service;

import com.codeki.tickets.Utils.Utils;
import com.codeki.tickets.dto.ResponseDto;
import com.codeki.tickets.exceptions.NotFoundException;
import com.codeki.tickets.models.Ticket;
import com.codeki.tickets.repository.TicketRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    Utils utils;

    public List<Map<String, Object>> findAll() {
        return utils.getCompleteTicketList(ticketRepository.findAll());
    }

    public List<Map<String, Object>> findByPassport(String passport) {
        List<Ticket> ticketList = ticketRepository.findByPassengerPassport(passport);
        if (!ticketList.isEmpty()) {
            return utils.getCompleteTicketList(ticketList);
        }
        throw new NotFoundException("No results found");
    }

    public List<Ticket> findByIdFlight(Long idFlight) {
        List<Ticket> ticketList = ticketRepository.findByIdFlight(idFlight);
        if (!ticketList.isEmpty()) {
            return ticketList;
        }
        throw new NotFoundException("No results found");
    }

    public Ticket createTicket(Long idFlight, Ticket newTicket) {
        try {
            flightFeignClient.getFlightById(idFlight);
            newTicket.setIdFlight(idFlight);
            return ticketRepository.save(newTicket);
        } catch (FeignException.NotFound exception) {
            throw new NotFoundException("Unable to create: Flight not found");
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
