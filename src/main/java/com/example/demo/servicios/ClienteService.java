package com.example.demo.servicios;
import com.example.demo.repositorio.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cliente;

@Service
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepositorio;

    @Autowired
    public ClienteService(ClienteRepository clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente obtenerClientePorId(int id) {
        Optional<Cliente> clienteOpt = clienteRepositorio.findById(id);
        return clienteOpt.orElse(null);
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public Cliente modificarCliente(Cliente cliente) {
        if (!clienteRepositorio.existsById(cliente.getId())) {
            throw new IllegalArgumentException("El cliente no existe.");
        }
        return clienteRepositorio.save(cliente);
    }

    @Override
    public void eliminarCliente(int id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new IllegalArgumentException("El cliente no existe.");
        }
        clienteRepositorio.deleteById(id);
    }

    @Override
    public boolean existeCliente(int id) {
        return clienteRepositorio.existsById(id);
    }

    @Override
    public List<Cliente> obtenerClientesPorNombre(String nombre) {
        return clienteRepositorio.findByNombreContainingIgnoreCase(nombre);
    }
}
