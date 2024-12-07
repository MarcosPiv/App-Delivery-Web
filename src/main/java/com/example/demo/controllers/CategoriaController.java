package com.example.demo.controllers;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.model.Categoria;
import com.example.demo.servicios.ICategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Categoria categoria = new Categoria(categoriaDTO.getId(), categoriaDTO.getDescripcion(), categoriaDTO.getTipoItem());
        Categoria categoriaGuardada = categoriaService.crearCategoria(categoria);

        CategoriaDTO respuestaDTO = new CategoriaDTO(
                categoriaGuardada.getId(),
                categoriaGuardada.getDescripcion(),
                categoriaGuardada.getTipoItem()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDTO);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaDTO> buscarCategoria(@PathVariable int id) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);

        CategoriaDTO categoriaDTO = new CategoriaDTO(
                categoria.getId(),
                categoria.getDescripcion(),
                categoria.getTipoItem()
        );

        return ResponseEntity.ok(categoriaDTO);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaDTO> modificarCategoria(
            @PathVariable int id,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {

        // Primero obtener la categoría existente
        Categoria categoriaExistente = categoriaService.buscarCategoriaPorId(id);

        // Actualizar campos
        categoriaExistente.setDescripcion(categoriaDTO.getDescripcion());
        categoriaExistente.setTipoItem(categoriaDTO.getTipoItem());

        Categoria categoriaActualizada = categoriaService.modificarCategoria(categoriaExistente);

        CategoriaDTO categoriaActualizadaDTO = new CategoriaDTO(
                categoriaActualizada.getId(),
                categoriaActualizada.getDescripcion(),
                categoriaActualizada.getTipoItem()
        );

        return ResponseEntity.ok(categoriaActualizadaDTO);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
