package com.example.demo.dto;

//import com.example.demo.serializers.ItemMenuDTODeserializer;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoItem"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ComidaDTO.class, name = "Comida"),
        @JsonSubTypes.Type(value = BebidaDTO.class, name = "Bebida")
})
public abstract class ItemMenuDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private CategoriaDTO categoria; // Se referencia al DTO de Categoria
    private double peso;

    // Métodos abstractos para diferenciar entre comida y bebida
    public abstract boolean esComida();
    public abstract boolean esBebida();
    public abstract boolean aptoVegano();
}
