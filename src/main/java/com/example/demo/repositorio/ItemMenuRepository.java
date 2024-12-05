package com.example.demo.repositorio;

import com.example.demo.clases.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMenuRepository extends JpaRepository<ItemMenu, Integer> {
}
