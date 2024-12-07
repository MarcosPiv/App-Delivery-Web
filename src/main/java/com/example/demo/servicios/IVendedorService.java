package com.example.demo.servicios;

import com.example.demo.model.Vendedor;

import java.util.List;

public interface IVendedorService {
    List<Vendedor> obtenerVendedoresPorNombre(String nombre);
    Vendedor crearVendedor(Vendedor vendedor);
    Vendedor modificarVendedor(Vendedor vendedor);
    void eliminarVendedor(int id);
    Vendedor obtenerVendedorPorId(int id);
    boolean existeVendedor(int id);
    List<Vendedor> obtenerTodosLosVendedores();
}
