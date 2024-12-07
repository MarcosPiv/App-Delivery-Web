package com.example.demo.servicios;

import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cliente;
import com.example.demo.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return clienteRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente con ID " + id + " no existe."));
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        if(clienteRepositorio.existsById(cliente.getId())) {
            throw new ResourceAlreadyExistsException("No se puede crear. El cliente con ID " + cliente.getId() + " ya existe.");
        }
        return clienteRepositorio.save(cliente);
    }

    @Override
    public Cliente modificarCliente(Cliente cliente) {
        if (!clienteRepositorio.existsById(cliente.getId())) {
            throw new ResourceNotFoundException("No se puede modificar. El cliente con ID " + cliente.getId() + " no existe.");
        }
        return clienteRepositorio.save(cliente);
    }

    @Override
    public void eliminarCliente(int id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. El cliente con ID " + id + " no existe.");
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

