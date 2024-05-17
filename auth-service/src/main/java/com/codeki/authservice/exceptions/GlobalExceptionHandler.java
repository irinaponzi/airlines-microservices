package com.codeki.authservice.exceptions;

import com.codeki.authservice.dto.ErrorResp;
import jakarta.servlet.ServletException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Recurso buscado no se encuentra
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResp> handleNotFoundException(NotFoundException e) {
        ErrorResp errorResp = new ErrorResp(404, e.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
    }

    // Key duplicada, ya existente
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResp> handleDuplicateKeyException(DuplicateKeyException e) {
        ErrorResp errorResp = new ErrorResp(400, e.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
    }

    // Excepción al procesar la solicitud del servlet
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResp> handleServletException(ServletException e) {
        ErrorResp errorResp = new ErrorResp(500, "Error occurred while processing request");
        return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Excepción de entrada/salida durante el procesamiento de la solicitud
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResp> handleIOException(IOException e) {
        ErrorResp errorResp = new ErrorResp(500, "Error occurred while processing request");
        return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}