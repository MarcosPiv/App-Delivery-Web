package com.example.demo.controllers;

import com.example.demo.clases.Categoria;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.servicios.ICategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final ICategoriaService categoriaService;

    @Autowired
    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<CategoriaDTO> crearNuevaCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria = new Categoria(categoriaDTO.getId(), categoriaDTO.getDescripcion(), categoriaDTO.getTipoItem());
            Categoria categoriaGuardada = categoriaService.crearCategoria(categoria);

            CategoriaDTO respuestaDTO = new CategoriaDTO(
                    categoriaGuardada.getId(),
                    categoriaGuardada.getDescripcion(),
                    categoriaGuardada.getTipoItem()
            );

            return ResponseEntity.ok(respuestaDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaDTO> buscarCategoria(@PathVariable int id) {
        try {
            Optional<Categoria> categoria = categoriaService.buscarCategoriaPorId(id);
            if (categoria.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            CategoriaDTO categoriaDTO = new CategoriaDTO(
                    categoria.get().getId(),
                    categoria.get().getDescripcion(),
                    categoria.get().getTipoItem()
            );

            return ResponseEntity.ok(categoriaDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaDTO> modificarCategoria(
            @PathVariable int id,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            Optional<Categoria> categoriaExistente = categoriaService.buscarCategoriaPorId(id);
            if (categoriaExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            categoriaExistente.get().setDescripcion(categoriaDTO.getDescripcion());
            categoriaExistente.get().setTipoItem(categoriaDTO.getTipoItem());

            Categoria categoriaActualizada = categoriaService.modificarCategoria(categoriaExistente.get());

            CategoriaDTO categoriaActualizadaDTO = new CategoriaDTO(
                    categoriaActualizada.getId(),
                    categoriaActualizada.getDescripcion(),
                    categoriaActualizada.getTipoItem()
            );

            return ResponseEntity.ok(categoriaActualizadaDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        try {
            boolean eliminado = categoriaService.eliminarCategoria(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
