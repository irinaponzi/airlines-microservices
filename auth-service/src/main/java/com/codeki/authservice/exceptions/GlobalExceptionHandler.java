package com.codeki.authservice.exceptions;

import com.codeki.authservice.dto.ReqResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Controller Advice para tratar las excepciones
    // NotFoundExcepcion: recurso buscado no se encuentra
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ReqResponse> handleNotFoundException(NotFoundException e) {
        ReqResponse reqResponse = new ReqResponse();
        reqResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(reqResponse, HttpStatus.NOT_FOUND);
    }
}