package com.example.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@DiscriminatorValue("Comida")
public class Comida extends ItemMenu{
    private int calorias;
    private boolean aptoVegano;
    private boolean aptoCeliaco;
    private double pesoSinEnvase;

    public Comida(int id,int calorias, boolean aptoVegano, boolean aptoCeliaco, double pesoSinEnvase,String nombre, String descripcion, double precio,Categoria categoria, double peso  ) {
        super.setId(id);
        super.setNombre(nombre);
        super.setDescripcion(descripcion);
        super.setPrecio(precio);
        super.setPeso(peso);
        super.setCategoria(categoria);
        this.calorias = calorias;
        this.aptoVegano = aptoVegano;
        this.aptoCeliaco = aptoCeliaco;
        this.pesoSinEnvase = pesoSinEnvase;
    }
    @Override
    public double peso(){
        setPeso(this.pesoSinEnvase*1.1);
        return getPeso();
    }
    @Override
    public boolean esComida() {
        return true;
    }

    @Override
    public boolean esBebida() {
        return false;
    }

    @Override
    public boolean aptoVegano() {
        return aptoVegano;
    }
}
