package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComidaDTO extends ItemMenuDTO {
    @Min(value = 0, message = "Las calorías no pueden ser negativas")
    private int calorias;

    @NotNull(message = "El campo aptoVegano no puede ser nulo")
    private Boolean aptoVegano;

    @NotNull(message = "El campo aptoCeliaco no puede ser nulo")
    private Boolean aptoCeliaco;

    @Min(value = 0, message = "El peso sin envase no puede ser negativo")
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
    public boolean isAptoVegano() {
        return aptoVegano;
    }

    public boolean isAptoCeliaco() {
        return aptoCeliaco;
    }
}
