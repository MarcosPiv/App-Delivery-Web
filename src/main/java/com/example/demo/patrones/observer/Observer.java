package com.example.demo.patrones.observer;

import com.example.demo.model.Pedido;

public interface Observer {
    void update(Pedido pedido);
}
