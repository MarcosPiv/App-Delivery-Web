package com.example.demo.servicios;

import com.example.demo.model.Categoria;

import java.util.Optional;

public interface ICategoriaService {
    Categoria crearCategoria(Categoria categoria);
    Categoria buscarCategoriaPorId(int id);
    Categoria modificarCategoria(Categoria categoriaExistente);
    void eliminarCategoria(int id);
    boolean existeCategoria(int id);
}
