package com.codeki.flightsapi.repository;

import com.codeki.flightsapi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByOriginContainingIgnoreCase(String origin);
    List<Flight> findByOriginIgnoreCaseAndDestinyIgnoreCase(String origin, String destiny);
    List<Flight> findByCompanyNameContainingIgnoreCase(String companyName);

}
