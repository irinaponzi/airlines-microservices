package com.codeki.tickets.repository;

import com.codeki.tickets.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByIdFlight(Long flightId);
    List<Ticket> findByIdUser(Long idUser);

}
