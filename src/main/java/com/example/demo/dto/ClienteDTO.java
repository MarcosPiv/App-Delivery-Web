package com.example.demo.dto;

import com.example.demo.clases.Coordenada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClienteDTO {
    private int id;
    private String cuit;
    private String email;
    private String direccion;
    private Coordenada coordenada;
    private String nombre;
}
