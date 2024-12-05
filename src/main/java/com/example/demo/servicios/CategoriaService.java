package com.example.demo.servicios;

import com.example.demo.clases.Categoria;
import com.example.demo.clases.ItemMenu;
import com.example.demo.repositorio.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria); // Devuelve la entidad guardada
    }

    public Optional<Categoria> buscarCategoriaPorId(int id) {
        return categoriaRepository.findById(id);
    }
}
