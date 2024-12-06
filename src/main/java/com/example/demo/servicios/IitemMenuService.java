package com.example.demo.servicios;

import com.example.demo.clases.ItemMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IitemMenuService {

    ItemMenu crearItemMenu(ItemMenu itemMenu);

    boolean existeItemMenu(int id);

    ItemMenu modificarItemMenu(ItemMenu itemMenu);

    void eliminarItemMenu(int id);

    ItemMenu obtenerItemMenu(int id);

    List<ItemMenu> obtenerItemsMenu();
}
