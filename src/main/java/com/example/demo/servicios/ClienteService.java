package com.example.demo.servicios;
import com.example.demo.repositorio.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.clases.Cliente;

@Service
public class ClienteService{

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> getClientes(){
        return this.clienteRepository.findAll();
    }

}
