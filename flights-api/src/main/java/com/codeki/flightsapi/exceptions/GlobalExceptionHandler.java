package com.codeki.flightsapi.exceptions;

import com.codeki.flightsapi.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Controller Advice para tratar las excepciones
    // NotFoundExcepcion: recurso buscado no se encuentra
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(NotFoundException e) {
        ResponseDto responseDto = new ResponseDto(e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    // RestClientException: excepciones al realizar el fetch a DolarApi
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ResponseDto> handleRestClientException() {
        ResponseDto responseDto = new ResponseDto("Error fetching dollar data");
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}