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

    // NotFoundExcepcion: recurso buscado no se encuentra
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResp> handleNotFoundException(NotFoundException e) {
        ErrorResp errorResp = new ErrorResp(404, e.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResp> handleDuplicateKeyException(DuplicateKeyException e) {
        ErrorResp errorResp = new ErrorResp(400, e.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResp> handleServletException(ServletException e) {
        ErrorResp errorResp = new ErrorResp(500, "Error occurred while processing request");
        return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResp> handleIOException(IOException e) {
        ErrorResp errorResp = new ErrorResp(500, "Error occurred while processing request");
        return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}