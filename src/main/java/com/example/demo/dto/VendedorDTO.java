package com.example.demo.dto;

import com.example.demo.model.Coordenada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VendedorDTO {
    private int id;
    private String nombre;
    private String direccion;
    private Coordenada coordenada;
}