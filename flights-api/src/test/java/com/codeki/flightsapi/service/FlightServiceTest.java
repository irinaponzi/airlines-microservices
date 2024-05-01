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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FlightServiceTest {

    @Mock
    static FlightRepository mockedFlightRepository;
    @Mock
    static CompanyRepository mockedCompanyRepository;
    @Mock
    Utils mockedUtils;

    @InjectMocks
    FlightService flightService;

    static Dollar mockedDollar;
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
        departureTime = LocalDateTime.of(2024, 2, 1, 20, 0, 0);
        arrivingTime = LocalDateTime.of(2024, 2, 1, 22, 0, 0);
        dollarPrice = 1200.0;

        company = new Company(1L, "Aerolíneas Argentinas", "https://www.aerolineas.com.ar", "banner1");

        flightList.add(new Flight(1L, "EZE", "COR", departureTime, arrivingTime, 10.0, "Diario", company));
        flightList.add(new Flight(2L, "USU", "EZE", departureTime, arrivingTime, 20.0, "Semanal", company));
        flightList.add(new Flight(3L, "EZE", "JUJ", departureTime, arrivingTime, 15.0, "Diario", company));

        flightDtoList.add(new FlightDto(1L, "EZE", "COR", departureTime, arrivingTime, 10.0 * dollarPrice, "Diario", company));
        flightDtoList.add(new FlightDto(2L, "USU", "EZE", departureTime, arrivingTime, 20.0 * dollarPrice, "Semanal", company));
        flightDtoList.add(new FlightDto(3L, "EZE", "JUJ", departureTime, arrivingTime, 15.0 * dollarPrice, "Diario", company));
    }

    @BeforeAll
    static void setMockedDollar() {
        mockedDollar = new Dollar("USD", "tarjeta", "Tarjeta",1000.0, 1400.0, LocalDateTime.of(2024, 3, 1, 10, 0, 0));
    }

    @AfterAll
    static void reset() {
        Mockito.reset(mockedFlightRepository);
    }

    @Test
    void getDollarCartTest() {
        Double dollarPriceExpected = dollarPrice;

        when(mockedUtils.fetchDollarCard()).thenReturn(mockedDollar);
        Double dollarPriceActual = flightService.getDollarCart();

        assertEquals(dollarPriceExpected, dollarPriceActual);
    }

    @Test
    void findAllTest() {
        List<Flight> flightListReturned = flightList;
        List<FlightDto> flightDtoListExpected = flightDtoList;

        when(mockedFlightRepository.findAll()).thenReturn(flightListReturned);
        when(mockedUtils.fetchDollarCard()).thenReturn(mockedDollar);
        when(mockedUtils.flightsListMapper(anyList(), anyDouble())).thenReturn(flightDtoListExpected);

        List<FlightDto> flightDtoListActual = flightService.findAll();

        assertEquals(flightDtoListExpected.size(), flightDtoListActual.size());
        assertEquals(flightDtoListExpected.get(0).getId(), flightDtoListActual.get(0).getId());
        assertEquals(flightDtoListExpected.get(1).getConvertedPrice(), flightDtoListActual.get(1).getConvertedPrice());
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        Flight flightExpected = flightList.get(0);

        when(mockedFlightRepository.findById(id)).thenReturn(Optional.of(flightExpected));
        Flight flightActual = flightService.findById(id);

        assertEquals(flightExpected.getId(), flightActual.getId());
        assertEquals(flightExpected.getOrigin(), flightActual.getOrigin());
        assertEquals(flightExpected.getDestiny(), flightActual.getDestiny());
        assertEquals(flightExpected.getPrice(), flightActual.getPrice());
    }

    @Test
    void findByOriginTestExceptionCase() {
        String origin = "xzxz";
        List<Flight> flightsListReturned = new ArrayList<>();
        NotFoundException exExpected = new NotFoundException("No results found");

        NotFoundException exActual = assertThrows(NotFoundException.class, () -> {
            when(mockedFlightRepository.findByOriginContainingIgnoreCase(origin)).thenReturn(flightsListReturned);
            flightService.findByOrigin(origin);
        });

        assertEquals(exExpected.getMessage(), exActual.getMessage());
    }

    @Test
    void getOffersTest() {
        Integer offerPrice = 18000;
        List<Flight> flightsOffersReturned = Arrays.asList(flightList.get(0), flightList.get(2));
        List<FlightDto> offersExpected = Arrays.asList(flightDtoList.get(0), flightDtoList.get(2));

        when(mockedFlightRepository.findAll()).thenReturn(flightList);
        when(mockedUtils.fetchDollarCard()).thenReturn(mockedDollar);
        when(mockedUtils.detectOffers(anyList(), anyDouble())).thenReturn(flightsOffersReturned);
        when(mockedUtils.flightsListMapper(anyList(), anyDouble())).thenReturn(offersExpected);

        List<FlightDto> offersActual = flightService.getOffers(offerPrice);

        assertEquals(offersExpected.size(), offersActual.size());
        assertEquals(offersExpected.get(0).getId(), offersActual.get(0).getId());
        assertEquals(offersExpected.get(1).getId(), offersActual.get(1).getId());
    }

    @Test
    void createTest() {
        Long id = company.getId();
        Flight flightToSave = new Flight("JUJ", "COR", departureTime, arrivingTime, 20.0 * dollarPrice, "Diario", company);

        when(mockedCompanyRepository.findById(id)).thenReturn(Optional.of(company));
        when(mockedFlightRepository.save(flightToSave)).thenReturn(flightToSave);

        Flight flightActual = flightService.create(id, flightToSave);

        assertEquals(flightToSave.getId(), flightActual.getId());
        assertEquals(flightToSave.getOrigin(), flightActual.getOrigin());
        assertEquals(flightToSave.getCompany().getName(), flightActual.getCompany().getName());
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;
        Flight flightToDeleted = flightList.get(0);
        ResponseDto responseDtoExpected = new ResponseDto("The flight " + id + " has been deleted");

        when(mockedFlightRepository.findById(id)).thenReturn(Optional.of(flightToDeleted));
        ResponseDto responseDtoActual = flightService.deleteById(id);

        assertEquals(responseDtoExpected.getMessage(), responseDtoActual.getMessage());
    }
}
