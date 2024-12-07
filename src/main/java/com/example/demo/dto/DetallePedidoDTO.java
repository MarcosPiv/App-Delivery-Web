package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetallePedidoDTO {
    private int id;
    private int cantidad;
    private double precio;
    private ItemMenuDTO itemMenu;
    private PedidoDTO pedido;
}

