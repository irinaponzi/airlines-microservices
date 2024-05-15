package com.codeki.flights.service;

import com.codeki.flights.dto.FlightDto;
import com.codeki.flights.dto.ResponseDto;
import com.codeki.flights.exceptions.NotFoundException;
import com.codeki.flights.model.Company;
import com.codeki.flights.model.Dollar;
import com.codeki.flights.model.Flight;
import com.codeki.flights.repository.CompanyRepository;
import com.codeki.flights.repository.FlightRepository;
import com.codeki.flights.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    Utils utils;

    public List<FlightDto> findAll() {
        List<Flight> flightsList = flightRepository.findAll();
        return utils.flightsListMapper(flightsList, getDollarCart());
    }

    public FlightDto findById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            return utils.flightMapper(flightOptional.get(), getDollarCart());
        }
        throw new NotFoundException("Flight not found");
    }

    public List<FlightDto> findByOrigin(String origin) {
        List<Flight> flightsList = flightRepository.findByOriginContainingIgnoreCase(origin);
        if (!flightsList.isEmpty()) {
            return utils.flightsListMapper(flightsList, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    public List<FlightDto> findByOriginAndDestiny(String origin, String destiny) {
        List<Flight> flightList = flightRepository.findByOriginIgnoreCaseAndDestinyIgnoreCase(origin, destiny);
        if (!flightList.isEmpty()) {
            return utils.flightsListMapper(flightList, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    public List<FlightDto> findByCompanyName(String companyName) {
        List<Flight> flightList = flightRepository.findByCompanyNameContainingIgnoreCase(companyName);
        if (!flightList.isEmpty()) {
            return utils.flightsListMapper(flightList, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    public List<FlightDto> getOffers(Integer offerPrice) {
        Double offerPriceToDollar = offerPrice / getDollarCart();
        List<Flight> flightsOffers = utils.detectOffers(flightRepository.findAll(), offerPriceToDollar);
        if (!flightsOffers.isEmpty()) {
            return utils.flightsListMapper(flightsOffers, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    public Flight create(Long companyId, Flight flight) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            flight.setCompany(companyOptional.get());
            return flightRepository.save(flight);
        }
        throw new NotFoundException("Unable to create: Company not found");
    }

    public Flight update(Long id, Flight flight) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flight.setId(id);
            flight.setCompany(flightOptional.get().getCompany());
            return flightRepository.save(flight);
        }
        throw new NotFoundException("Unable to update: Flight not found");
    }

    public ResponseDto deleteById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flightRepository.deleteById(id);
            return new ResponseDto("The flight " + id + " has been deleted");
        }
        throw new NotFoundException("Unable to delete: Flight not found");
    }

    // Retorna el valor del dolar tarjeta que se utilizará: un promedio
    protected Double getDollarCart() {
        Dollar dollar = utils.fetchDollarCard();
        return dollar.getPromedio();
    }
}
