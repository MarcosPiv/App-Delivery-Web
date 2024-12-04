package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.clases.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>  {

    
} 