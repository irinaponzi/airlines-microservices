package com.codeki.flights.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDto {

    // ResponseDto se utiliza para mensajes simples de respuesta, que dan alguna información en un String
    private String message;

}
