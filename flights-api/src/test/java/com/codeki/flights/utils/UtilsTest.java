package com.codeki.flights.utils;

import com.codeki.flights.dto.FlightDto;
import com.codeki.flights.model.Company;
import com.codeki.flights.model.Flight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UtilsTest {

    @Autowired
    Utils utils;

    static List<Flight> flightList;
    static List<FlightDto> flightDtoList;
    static Company company;
    static LocalDateTime departureTime;
    static LocalDateTime arrivingTime;
    static Double dollarPrice;

    @BeforeAll
    static void setUp() {

        flightList = new ArrayList<>();
        flightDtoList = new ArrayList<>();
        departureTime = LocalDateTime.of(2024, 3, 1, 10, 0, 0);
        arrivingTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        company = new Company(1L, "Aerolíneas Argentinas", "https://www.aerolineas.com.ar/", "banner");
        dollarPrice = 1200.0;

        flightList.add(new Flight(1L, "EZE", "COR", departureTime, arrivingTime, 10.0, "Semanal", company));
        flightList.add(new Flight(2L, "EZE", "USU", departureTime, arrivingTime, 80.0, "Mensual", company));
        flightList.add(new Flight(3L, "EZE", "MDQ", departureTime, arrivingTime, 8.0, "Diario", company));

        flightDtoList.add(new FlightDto(1L, "EZE", "COR", departureTime, arrivingTime, 10.0 * dollarPrice, "Semanal", company));
        flightDtoList.add(new FlightDto(2L, "EZE", "USU", departureTime, arrivingTime, 80.0 * dollarPrice, "Mensual", company));
        flightDtoList.add(new FlightDto(3L, "EZE", "MDQ", departureTime, arrivingTime, 8.0 * dollarPrice, "Diario", company));
    }

    @Test
    void flightMapperTest() {
        Flight flight = flightList.get(0);
        FlightDto flightDtoExpected = flightDtoList.get(0);

        FlightDto flightDtoActual = utils.flightMapper(flight, dollarPrice);

        assertEquals(flightDtoExpected.getConvertedPrice(), flightDtoActual.getConvertedPrice());
        assertEquals(flightDtoExpected.getId(), flightDtoActual.getId());
    }

    @Test
    void flightsListMapperTest() {
        List<FlightDto> flightDtoListExpected = flightDtoList;
        List<FlightDto> flightDtoListActual = utils.flightsListMapper(flightList, dollarPrice);

        assertEquals(flightDtoListExpected.size(), flightDtoListActual.size());
        assertThat(flightDtoListActual)
                .allMatch(f -> f instanceof FlightDto);
    }

    @Test
    void detectOffersTest() {
        Double offerPrice = 10.0;
        List<Flight> offersExpected = Arrays.asList(flightList.get(2), flightList.get(0));

        List<Flight> offersActual = utils.detectOffers(flightList, offerPrice);

        assertEquals(offersExpected.size(), offersActual.size());
        assertEquals(offersExpected.get(0).getId(), offersActual.get(0).getId());
        assertEquals(offersExpected.get(1).getId(), offersActual.get(1).getId());
    }
}
