package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoItem", discriminatorType = DiscriminatorType.STRING)
public abstract class ItemMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private double peso;

    public abstract double peso();
    public abstract boolean esComida();
    public abstract boolean esBebida();
    public abstract boolean aptoVegano();
}
