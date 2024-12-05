package com.example.demo.servicios;

import com.example.demo.clases.ItemMenu;
import com.example.demo.repositorio.ItemMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemMenuService implements IitemMenuService {

    private final ItemMenuRepository itemMenuRepository;
    @Autowired
    public ItemMenuService(ItemMenuRepository itemMenuRepository) {
        this.itemMenuRepository = itemMenuRepository;
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

    public ItemMenu modificarItemMenu(ItemMenu itemMenu) {
        if (!itemMenuRepository.existsById(itemMenu.getId())) {
            throw new IllegalArgumentException("El ítem con ID " + itemMenu.getId() + " no existe.");
        }
        return itemMenuRepository.save(itemMenu); // Guarda y retorna el ítem modificado
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
