package com.example.demo.servicios;

import com.example.demo.model.Categoria;
import com.example.demo.repositorio.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Crear una nueva categoría
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria); // Devuelve la entidad guardada
    }

    // Buscar una categoría por su ID
    public Optional<Categoria> buscarCategoriaPorId(int id) {
        return categoriaRepository.findById(id);
    }

    // Modificar una categoría existente
    public Categoria modificarCategoria(Categoria categoriaExistente) {
        if (!categoriaRepository.existsById(categoriaExistente.getId())) {
            throw new IllegalArgumentException("La categoría con ID " + categoriaExistente.getId() + " no existe.");
        }

        // Actualizar los campos
        Categoria categoriaActualizada = categoriaRepository.findById(categoriaExistente.getId())
                .map(categoria -> {
                    categoria.setDescripcion(categoriaExistente.getDescripcion());
                    categoria.setTipoItem(categoriaExistente.getTipoItem());
                    return categoria;
                })
                .orElseThrow(() -> new IllegalArgumentException("Error al actualizar la categoría con ID " + categoriaExistente.getId()));

        // Guardar y devolver la categoría modificada
        return categoriaRepository.save(categoriaActualizada);
    }

    // Eliminar una categoría por su ID
    public boolean eliminarCategoria(int id) {
        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("La categoría con ID " + id + " no existe.");
        }

        // Eliminar la categoría
        categoriaRepository.deleteById(id);
        return true; // Retorna true si la operación fue exitosa
    }
}
