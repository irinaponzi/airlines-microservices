package com.codeki.tickets.Utils;

import com.codeki.tickets.dto.FlightDto;
import com.codeki.tickets.dto.UserDto;
import com.codeki.tickets.models.Ticket;
import com.codeki.tickets.service.FlightFeignClient;
import com.codeki.tickets.service.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Utils {

    @Autowired
    FlightFeignClient flightFeignClient;
    @Autowired
    UserFeignClient userFeignClient;

    public Map<String, Object> getCompleteTicket(Ticket ticket) {
        FlightDto flightDto = flightFeignClient.getFlightById(ticket.getIdFlight()).getBody();
        UserDto userDto = userFeignClient.getUserById(ticket.getIdUser()).getBody();

        Map<String, Object> completeTicket = new HashMap<>();
        completeTicket.put("Ticket", ticket);
        completeTicket.put("User", userDto);
        completeTicket.put("Flight", flightDto);

        return completeTicket;
    }

    public List<Map<String, Object>> getCompleteTicketList(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::getCompleteTicket)
                .collect(Collectors.toList());
    }

}
