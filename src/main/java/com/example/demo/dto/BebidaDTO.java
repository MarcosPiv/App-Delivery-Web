package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BebidaDTO extends ItemMenuDTO {
    @Min(value = 0, message = "La graduación alcohólica no puede ser negativa")
    private double graduacionAlcoholica;

    @Min(value = 0, message = "El tamaño no puede ser negativo")
    private double tamanio;

    @Override
    public boolean esComida() {
        return false;
    }

    @Override
    public boolean esBebida() {
        return true;
    }

    @Override
    public boolean isAptoVegano() {
        return false; // Asumido según la lógica del modelo
    }
}
