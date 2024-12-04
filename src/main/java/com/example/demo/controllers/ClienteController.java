package com.example.demo.controllers;
import com.example.demo.clases.Cliente;
import com.example.demo.servicios.*;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }

    @GetMapping
    public List<Cliente> getClientes(){
        return this.clienteService.getClientes();
    }


}
