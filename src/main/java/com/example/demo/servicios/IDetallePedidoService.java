package com.example.demo.servicios;

import com.example.demo.model.DetallePedido;

public interface IDetallePedidoService {
    DetallePedido crearDetallePedido(DetallePedido detallePedido);
    DetallePedido actualizarDetallePedido(DetallePedido detallePedido);
}
