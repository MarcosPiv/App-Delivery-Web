package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComidaDTO extends ItemMenuDTO {
    private int calorias;
    private boolean aptoVegano;
    private boolean aptoCeliaco;
    private double pesoSinEnvase;

    @Override
    public boolean esComida() {
        return true;
    }

    @Override
    public boolean esBebida() {
        return false;
    }

    @Override
    public boolean aptoVegano() {
        return aptoVegano;
    }
}
