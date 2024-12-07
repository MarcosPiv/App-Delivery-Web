package com.example.demo.dto;

import com.example.demo.model.Estado;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoDTO {
    private int id;

    @NotNull(message = "El cliente no puede ser nulo")
    private int clienteId;

    @NotNull(message = "El vendedor no puede ser nulo")
    private int vendedorId;

    @PositiveOrZero(message = "El precio total no puede ser negativo")
    private double precioTotal;

    @Setter
    @NotNull(message = "La lista de detalles no puede ser nula")
    @NotEmpty(message = "Debe existir al menos un detalle en el pedido")
    private List<DetallePedidoDTO> detallesPedido;

    @NotNull(message = "El estado no puede ser nulo")
    private Estado estado;

}
