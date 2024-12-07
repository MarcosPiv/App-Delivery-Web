package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class Pago {
    private double monto;
    private Date fecha;
    private String tipoPago;

    public Pago(double monto, Date fecha) {
        this.monto = monto;
        this.fecha = fecha;
    }
}
