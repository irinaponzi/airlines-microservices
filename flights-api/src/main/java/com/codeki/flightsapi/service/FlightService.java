package com.codeki.flightsapi.service;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.exceptions.NotFoundException;
import com.codeki.flightsapi.model.Company;
import com.codeki.flightsapi.model.Dollar;
import com.codeki.flightsapi.model.Flight;
import com.codeki.flightsapi.repository.CompanyRepository;
import com.codeki.flightsapi.repository.FlightRepository;
import com.codeki.flightsapi.utils.Utils;
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

    // Busca todos los vuelos y los retorna como FlightDto
    public List<FlightDto> findAll() {
        List<Flight> flightsList = flightRepository.findAll();
        return utils.flightsListMapper(flightsList, getDollarCart());
    }

    // Busca un vuelo por su ID y lo retorna. Si no lo encuentra devuelve una excepción
    public FlightDto findById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            return utils.flightMapper(flightOptional.get(), getDollarCart());
        }
        throw new NotFoundException("Flight not found");
    }

    // Busca vuelos que contengan en su origen el String pasado por parámetro, ignorando mayúsculas y minúsculas
    // Retorna una lista de FlightDto con los vuelos encontrados. Si la lista está vacía devuelve una excepción
    public List<FlightDto> findByOrigin(String origin) {
        List<Flight> flightsList = flightRepository.findByOriginContainingIgnoreCase(origin);
        if (!flightsList.isEmpty()) {
            return utils.flightsListMapper(flightsList, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    // Busca vuelos por su origen y destino, ignorando mayúsculas y minúsculas
    // Retorna una lista de FlightDto con los vuelos encontrados. Si la lista está vacía devuelve una excepción
    public List<FlightDto> findByOriginAndDestiny(String origin, String destiny) {
        List<Flight> flightList = flightRepository.findByOriginIgnoreCaseAndDestinyIgnoreCase(origin, destiny);
        if (!flightList.isEmpty()) {
            return utils.flightsListMapper(flightList, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    // Busca vuelos por la compañía a la que pertenecen, según si su nombre contiene el String pasado por parámetro, ignorando mayúsculas y minúsculas
    // Retorna una lista de FlightDto con los vuelos encontrados. Si la lista está vacía devuelve una excepción
    public List<FlightDto> findByCompanyName(String companyName) {
        List<Flight> flightList = flightRepository.findByCompanyNameContainingIgnoreCase(companyName);
        if (!flightList.isEmpty()) {
            return utils.flightsListMapper(flightList, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    // Busca vuelos cuyo precio sea igual o menor al precio en PESOS pasado por parámetro
    // Retorna una lista de FlightDto con los vuelos encontrados ordenados por su precio. Si la lista está vacía devuelve una excepción
    public List<FlightDto> getOffers(Integer offerPrice) {
        Double offerPriceToDollar = offerPrice / getDollarCart();
        List<Flight> flightsOffers = utils.detectOffers(flightRepository.findAll(), offerPriceToDollar);
        if (!flightsOffers.isEmpty()) {
            return utils.flightsListMapper(flightsOffers, getDollarCart());
        }
        throw new NotFoundException("No results found");
    }

    // Guarda un nuevo vuelo, al que se le settea la compañía por su ID, y lo retorna
    // Si no encuentra la compañía no guarda el vuelo y devuelve una excepción
    public Flight create(Long companyId, Flight flight) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            flight.setCompany(companyOptional.get());
            return flightRepository.save(flight);
        }
        throw new NotFoundException("Unable to create: Company not found");
    }

    // Actualiza los datos de un vuelo existente, buscado por su ID, y lo retorna
    // Si no lo encuentra no realiza la actualización y devuelve una excepción
    public Flight update(Long id, Flight flight) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flight.setId(id);
            flight.setCompany(flightOptional.get().getCompany());
            return flightRepository.save(flight);
        }
        throw new NotFoundException("Unable to update: Flight not found");
    }

    // Elimina un vuelo, buscado por su ID, y retorna un mensaje confirmando la acción
    // Si no lo encuentra devuelve una excepción
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
