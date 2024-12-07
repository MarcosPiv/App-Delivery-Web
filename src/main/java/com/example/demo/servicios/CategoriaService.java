package com.example.demo.servicios;

import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Categoria;
import com.example.demo.repositorio.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria crearCategoria(Categoria categoria) {
        if (categoriaRepository.existsById(categoria.getId())) {
            throw new ResourceAlreadyExistsException("No se puede crear. La categoría con ID " + categoria.getId() + " ya existe.");
        }
        return categoriaRepository.save(categoria);
    }


    public Categoria buscarCategoriaPorId(int id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con ID " + id + " no existe."));
    }

    public Categoria modificarCategoria(Categoria categoriaExistente) {
        if (!categoriaRepository.existsById(categoriaExistente.getId())) {
            throw new ResourceNotFoundException("La categoría con ID " + categoriaExistente.getId() + " no existe.");
        }

        Categoria categoriaActualizada = categoriaRepository.findById(categoriaExistente.getId())
                .map(categoria -> {
                    categoria.setDescripcion(categoriaExistente.getDescripcion());
                    categoria.setTipoItem(categoriaExistente.getTipoItem());
                    return categoria;
                })
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con ID " + categoriaExistente.getId() + " no existe."));

        return categoriaRepository.save(categoriaActualizada);
    }

    public void eliminarCategoria(int id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("La categoría con ID " + id + " no existe.");
        }
        categoriaRepository.deleteById(id);
    }

    public boolean existeCategoria(int id) {
        return categoriaRepository.existsById(id);
    }
}
