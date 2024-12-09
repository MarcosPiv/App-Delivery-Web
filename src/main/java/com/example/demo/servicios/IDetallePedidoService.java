package com.example.demo.servicios;

import com.example.demo.model.DetallePedido;

import java.util.Optional;

public interface IDetallePedidoService {
    DetallePedido crearDetallePedido(DetallePedido detallePedido);
    DetallePedido actualizarDetallePedido(DetallePedido detallePedido);
    Optional<DetallePedido> buscarPorCampos(int itemId, int cantidad, double precio);
    boolean existeDetallePedido(int id);
}
