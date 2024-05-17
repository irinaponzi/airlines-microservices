package com.codeki.tickets.exceptions;

import com.codeki.tickets.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // NotFoundException: recurso buscado no se encuentra
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(NotFoundException e) {
        ResponseDto responseDto = new ResponseDto(e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

}