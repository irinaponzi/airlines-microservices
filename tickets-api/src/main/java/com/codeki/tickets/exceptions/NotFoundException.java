package com.codeki.tickets.exceptions;

public class NotFoundException extends RuntimeException {

    // Excepción personalizada para tratar casos en donde no se halla el recurso buscado
    public NotFoundException(String message) {
        super(message);
    }
}
