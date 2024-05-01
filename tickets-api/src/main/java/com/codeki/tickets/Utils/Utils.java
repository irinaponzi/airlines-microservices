package com.codeki.tickets.Utils;

import com.codeki.tickets.dto.FlightDto;
import com.codeki.tickets.models.Ticket;
import com.codeki.tickets.service.FlightClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Utils {

    @Autowired
    FlightClient flightClient;

    public Map<String, Object> getCompleteTicket(Ticket ticket) {
        FlightDto flightDto = flightClient.getFlightById(ticket.getIdFlight()).getBody();

        Map<String, Object> completeTicket = new HashMap<>();
        completeTicket.put("Ticket", ticket);
        completeTicket.put("Flight", flightDto);

        return completeTicket;
    }

    public List<Map<String, Object>> getCompleteTicketList(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::getCompleteTicket)
                .collect(Collectors.toList());
    }

}
