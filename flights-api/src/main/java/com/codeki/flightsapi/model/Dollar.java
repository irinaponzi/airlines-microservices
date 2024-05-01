package com.codeki.flightsapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dollar {

    // Modelo para el fetch a DolarApi
    private String moneda;
    private String casa;
    private String nombre;
    private Double compra;
    private Double venta;
    private LocalDateTime fechaActualizacion;

    // Calcula el promedio entre el valor de compra y venta del dolar
    public Double getPromedio() {
        return ((compra + venta) / 2);
    }
}
