package com.example.demo.clases;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@DiscriminatorValue("Bebida")
public class Bebida extends ItemMenu{
    private double graduacionAlcoholica;
    private double tamanio;

    public Bebida(int id,double graduacionAlcoholica, double tamanio,String nombre, String descripcion, double precio,Categoria categoria, double peso  ) {
        super.setId(id);
        super.setNombre(nombre);
        super.setDescripcion(descripcion);
        super.setPrecio(precio);
        super.setPeso(peso);
        super.setCategoria(categoria);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.tamanio = tamanio;
    }

    @Override
    public double peso(){
        if(graduacionAlcoholica == 0){
            setPeso((this.graduacionAlcoholica*1.04)*1.2);
        }
        else{
            setPeso((this.graduacionAlcoholica*0.99)*1.2);
        }
        return getPeso();
    }
    @Override
    public boolean esComida() {
        return false;
    }

    @Override
    public boolean esBebida() {
        return true;
    }

    @Override
    public boolean aptoVegano() {
        return false;
    }
}
