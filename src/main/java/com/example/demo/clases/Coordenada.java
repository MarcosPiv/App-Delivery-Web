package com.example.demo.clases;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter

@Embeddable // Indica que es un atributo embebido
public class Coordenada {
    private double lat;
    private double lng;

    public Coordenada(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}