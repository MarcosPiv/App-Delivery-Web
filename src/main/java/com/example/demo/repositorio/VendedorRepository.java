package com.example.demo.repositorio;

import com.example.demo.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
    List<Vendedor> findByNombreContainingIgnoreCase(String nombre);
}
