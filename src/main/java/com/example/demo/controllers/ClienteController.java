package com.example.demo.controllers;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.mappers.ClienteMapper;
import com.example.demo.model.Cliente;
import com.example.demo.servicios.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.noContent().build();
        }

        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(clienteMapper::convertirADTO)
                .toList();

        return ResponseEntity.ok(clientesDTO);
    }

    @PostMapping("/crearCliente")
    public ResponseEntity<ClienteDTO> crearNuevoCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.convertirAEntidad(clienteDTO);
        Cliente clienteGuardado = clienteService.crearCliente(cliente);
        ClienteDTO clienteDTOGuardado = clienteMapper.convertirADTO(clienteGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTOGuardado);
    }

    @PutMapping("/modificarCliente/{id}")
    public ResponseEntity<ClienteDTO> modificarCliente(@PathVariable int id, @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.convertirAEntidad(clienteDTO);
        cliente.setId(id);
        Cliente clienteModificado = clienteService.modificarCliente(cliente);
        ClienteDTO clienteDTOModificado = clienteMapper.convertirADTO(clienteModificado);
        return ResponseEntity.ok(clienteDTOModificado);
    }

    @DeleteMapping("/borrarCliente/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable int id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        ClienteDTO clienteDTO = clienteMapper.convertirADTO(cliente);
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<ClienteDTO>> buscarClientePorNombre(@RequestParam String nombre) {
        List<Cliente> clientes = clienteService.obtenerClientesPorNombre(nombre);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(clienteMapper::convertirADTO)
                .toList();

        return ResponseEntity.ok(clientesDTO);
    }
}

