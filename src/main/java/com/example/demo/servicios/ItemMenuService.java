package com.example.demo.servicios;

import com.example.demo.clases.Categoria;
import com.example.demo.clases.ItemMenu;
import com.example.demo.repositorio.CategoriaRepository;
import com.example.demo.repositorio.ItemMenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemMenuService implements IitemMenuService {

    private final ItemMenuRepository itemMenuRepository;
    private final CategoriaRepository categoriaRepository;
    @Autowired
    public ItemMenuService(ItemMenuRepository itemMenuRepository, CategoriaRepository categoriaRepository) {
        this.itemMenuRepository = itemMenuRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ItemMenu> obtenerItemsMenu() {
        return itemMenuRepository.findAll();
    }

    public ItemMenu crearItemMenu(ItemMenu itemMenu) {
        return itemMenuRepository.save(itemMenu);
    }

    public boolean existeItemMenu(int id) {
        return itemMenuRepository.existsById(id);
    }

    @Transactional
    public ItemMenu modificarItemMenu(ItemMenu itemMenu) {
        // Verificar si el ItemMenu existe en la base de datos
        ItemMenu itemMenuExistente = itemMenuRepository.findById(itemMenu.getId())
                .orElseThrow(() -> new IllegalArgumentException("El ítem con ID " + itemMenu.getId() + " no existe."));

        // Actualizar los campos del ItemMenu existente
        itemMenuExistente.setNombre(itemMenu.getNombre());
        itemMenuExistente.setDescripcion(itemMenu.getDescripcion());
        itemMenuExistente.setPrecio(itemMenu.getPrecio());
        itemMenuExistente.setPeso(itemMenu.getPeso());

        // Verificar si la categoría asociada es válida
        Categoria categoria = itemMenu.getCategoria();
        if (categoria == null || categoria.getId() == 0) {
            throw new IllegalArgumentException("El ItemMenu debe tener una categoría con un ID válido.");
        }

        // Actualizar la categoría existente o crear una nueva si no existe
        Categoria categoriaExistente = categoriaRepository.findById(categoria.getId())
                .orElseGet(() -> {
                    if (categoria.getDescripcion() == null || categoria.getTipoItem() == null) {
                        throw new IllegalArgumentException("Para crear una nueva categoría, se requieren 'descripcion' y 'tipoItem'");
                    }
                    Categoria nuevaCategoria = new Categoria();
                    nuevaCategoria.setDescripcion(categoria.getDescripcion());
                    nuevaCategoria.setTipoItem(categoria.getTipoItem());
                    return categoriaRepository.save(nuevaCategoria);
                });

        itemMenuExistente.setCategoria(categoriaExistente);

        // Guardar y retornar el ItemMenu modificado
        return itemMenuRepository.save(itemMenuExistente);
    }









    public void eliminarItemMenu(int id) {
        if (!itemMenuRepository.existsById(id)) {
            throw new IllegalArgumentException("El ítem con ID " + id + " no existe.");
        }
        itemMenuRepository.deleteById(id);
    }

    public ItemMenu obtenerItemMenu(int id) {
        return itemMenuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El ítem con ID " + id + " no existe."));
    }

}
