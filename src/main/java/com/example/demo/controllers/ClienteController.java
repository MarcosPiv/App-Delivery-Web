package com.example.demo.controllers;
import com.example.demo.clases.Cliente;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.mappers.ClienteMapper;
import com.example.demo.servicios.*;


import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final IClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteController(IClienteService clienteService, ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @GetMapping("/mostrarTodos")
    public ResponseEntity<List<ClienteDTO>> mostrarListaClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(clienteMapper::convertirADTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientesDTO);
    }

    @PostMapping("/crearCliente")
    public ResponseEntity<ClienteDTO> crearNuevoCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteMapper.convertirAEntidad(clienteDTO);
            Cliente clienteGuardado = clienteService.crearCliente(cliente);
            ClienteDTO clienteDTOGuardado = clienteMapper.convertirADTO(clienteGuardado);

            return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTOGuardado);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/modificarCliente/{id}")
    public ResponseEntity<ClienteDTO> modificarCliente(@PathVariable int id, @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            if (!clienteService.existeCliente(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Cliente cliente = clienteMapper.convertirAEntidad(clienteDTO);
            cliente.setId(id);
            Cliente clienteModificado = clienteService.modificarCliente(cliente);
            ClienteDTO clienteDTOModificado = clienteMapper.convertirADTO(clienteModificado);

            return ResponseEntity.ok(clienteDTOModificado);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/borrarCliente/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        try {
            if (!clienteService.existeCliente(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            clienteService.eliminarCliente(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable int id) {
        try {
            Cliente cliente = clienteService.obtenerClientePorId(id);

            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            ClienteDTO clienteDTO = clienteMapper.convertirADTO(cliente);
            return ResponseEntity.ok(clienteDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<ClienteDTO>> buscarClientePorNombre(@RequestParam String nombre) {
        List<Cliente> clientes = clienteService.obtenerClientesPorNombre(nombre);

        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(clienteMapper::convertirADTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientesDTO);
    }
}
