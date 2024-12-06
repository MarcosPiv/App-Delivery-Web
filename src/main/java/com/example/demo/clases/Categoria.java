package com.example.demo.clases;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String descripcion;
    private String tipoItem /*Bebida o Comida*/;
    public Categoria(int id, String descripcion, String tipoItem) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoItem = tipoItem;
    }
}
