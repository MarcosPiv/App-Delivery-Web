package com.example.demo.servicios;

import com.example.demo.model.Pedido;

import java.util.List;

public interface IPedidoService {
    List<Pedido> listarPedidos();
    Pedido crearPedido(Pedido pedido);
    Pedido buscarPedidoPorId(int id);
    boolean existePedido(int id);
    Pedido actualizarPedido(Pedido pedido);
    void eliminarPedido(int id);
    void cambiarEstadoPedido(int id, String nuevoEstado);
}
