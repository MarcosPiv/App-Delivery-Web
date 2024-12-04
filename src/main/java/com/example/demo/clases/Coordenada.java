package com.example.demo.clases;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coordenada {
    private double lat;
    private double lng;

    public Coordenada(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }   
}
