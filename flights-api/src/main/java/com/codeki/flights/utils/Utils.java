package com.codeki.flights.utils;

import com.codeki.flights.dto.FlightDto;
import com.codeki.flights.model.Dollar;
import com.codeki.flights.model.Flight;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Utils {

    @Value("${dollar.card-url}")
    private String URL_DOLLAR_CARD;

    private RestTemplate restTemplate = restTemplate();

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Fetch a DolarApi para los valores del dolar tarjeta
    public Dollar fetchDollarCard() {
        return restTemplate.getForObject(URL_DOLLAR_CARD, Dollar.class);
    }

    // Mapper de Flight a FlightDto
    public FlightDto flightMapper(Flight flight, Double dollar) {
        return new FlightDto(flight.getId(),flight.getOrigin(), flight.getDestiny(), flight.getDepartureTime(),
                flight.getArrivingTime(), flight.getPrice() * dollar, flight.getFrequency(), flight.getCompany());
    }

    // Mapper para listas: recibe una lista de Flight y devuelve una lista de FlightDto. Mapea llamando al método flightMapper
    public List<FlightDto> flightsListMapper(List<Flight> flightsList, Double dollar) {
        return flightsList.stream()
                .map(flight -> flightMapper(flight, dollar))
                .collect(Collectors.toList());
    }

    // Busca vuelos con precios iguales o menores al offerPrice pasado por parámetro
    public List<Flight> detectOffers(List<Flight> flights, Double offerPrice) {
        // Se filtra y el resultado se ordena de menor a mayor precio
        return flights.stream()
                .filter(flightDto -> flightDto.getPrice() <= offerPrice)
                .sorted(Comparator.comparing(Flight::getPrice))
                .collect(Collectors.toList());
    }
}
