package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter

@Entity
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemMenu item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
    private int cantidad;
    private double precio;

    public DetallePedido(int id, ItemMenu item, int cantidad, double precio, Pedido pedido) {
        this.id = id;
        this.item = item;
        this.pedido = pedido;
        this.cantidad = cantidad;
        this.precio = precio;
    }
}
