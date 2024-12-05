package com.example.demo.controllers;



import com.example.demo.clases.Categoria;
import com.example.demo.clases.ItemMenu;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.ItemMenuDTO;
import com.example.demo.mappers.ItemMenuMapper;
import com.example.demo.servicios.ICategoriaService;
import com.example.demo.servicios.ItemMenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/itemMenu")
public class ItemMenuController {
    private final ICategoriaService categoriaService;
    private final ItemMenuService itemMenuService;
    private final ItemMenuMapper itemMenuMapper;

    @Autowired
    public ItemMenuController(ICategoriaService categoriaService, ItemMenuService itemMenuService, ItemMenuMapper itemMenuMapper) {
        this.categoriaService = categoriaService;
        this.itemMenuService = itemMenuService;
        this.itemMenuMapper = itemMenuMapper;
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDTO> crearNuevaCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {

        Categoria categoria = new Categoria(categoriaDTO.getId(), categoriaDTO.getDescripcion(), categoriaDTO.getTipoItem());

        Categoria categoriaGuardada = categoriaService.crearCategoria(categoria);

        CategoriaDTO respuestaDTO = new CategoriaDTO(
                categoriaGuardada.getId(),
                categoriaGuardada.getDescripcion(),
                categoriaGuardada.getTipoItem()
        );

        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemMenuDTO>> mostrarListaItems() {
        List<ItemMenu> items = itemMenuService.obtenerItemsMenu();

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Retorna 204 si la lista está vacía
        }

        // Conversión de entidades a DTOs
        List<ItemMenuDTO> itemsDTO = items.stream()
                .map(itemMenuMapper::convertirADTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(itemsDTO);
    }

@PostMapping("/crearItem")
public ResponseEntity<ItemMenuDTO> crearItemMenu(@Valid @RequestBody ItemMenuDTO itemMenuDTO) {
    try {
        ItemMenu itemMenu = itemMenuMapper.convertirAEntidad(itemMenuDTO);
        ItemMenu itemMenuGuardado = itemMenuService.crearItemMenu(itemMenu);
        ItemMenuDTO itemMenuDTOGuardado = itemMenuMapper.convertirADTO(itemMenuGuardado);
        return ResponseEntity.ok(itemMenuDTOGuardado);
    } catch (Exception ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}

    @PutMapping("/modificarItem")
    public ResponseEntity<ItemMenuDTO> modificarItemMenu(@Valid @RequestBody ItemMenuDTO itemMenuDTO) {
        try {
            ItemMenu itemMenu = itemMenuMapper.convertirAEntidad(itemMenuDTO);

            if (!itemMenuService.existeItemMenu(itemMenu.getId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            ItemMenu itemMenuModificado = itemMenuService.modificarItemMenu(itemMenu);

            ItemMenuDTO itemMenuDTOModificado = itemMenuMapper.convertirADTO(itemMenuModificado);

            return ResponseEntity.ok(itemMenuDTOModificado);
        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/eliminarItem/{id}")
    public ResponseEntity<Void> eliminarItemMenu(@PathVariable int id) {
        try {
            itemMenuService.eliminarItemMenu(id);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/buscarItem/{id}")
    public ResponseEntity<ItemMenuDTO> buscarItemMenu(@PathVariable int id) {
        try {
            ItemMenu itemMenu = itemMenuService.obtenerItemMenu(id);

            ItemMenuDTO itemMenuDTO = itemMenuMapper.convertirADTO(itemMenu);

            return ResponseEntity.ok(itemMenuDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


}
