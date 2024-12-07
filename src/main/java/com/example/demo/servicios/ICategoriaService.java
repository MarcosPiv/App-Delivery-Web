package com.example.demo.servicios;

import com.example.demo.model.Categoria;

import java.util.Optional;

public interface ICategoriaService {
    Categoria crearCategoria(Categoria categoria);
    Optional<Categoria> buscarCategoriaPorId(int id);
    Categoria modificarCategoria(Categoria categoriaExistente);
    boolean eliminarCategoria(int id);
}
