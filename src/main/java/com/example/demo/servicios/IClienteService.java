package com.example.demo.servicios;

import com.example.demo.clases.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> obtenerTodosLosClientes();

    Cliente obtenerClientePorId(int id);

    Cliente crearCliente(Cliente cliente);

    Cliente modificarCliente(Cliente cliente);

    void eliminarCliente(int id);

    boolean existeCliente(int id);

    List<Cliente> obtenerClientesPorNombre(String nombre);
}