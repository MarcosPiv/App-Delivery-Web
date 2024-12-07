package com.example.demo.mappers;

import com.example.demo.model.Bebida;
import com.example.demo.model.Categoria;
import com.example.demo.model.Comida;
import com.example.demo.model.ItemMenu;
import com.example.demo.dto.BebidaDTO;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.ComidaDTO;
import com.example.demo.dto.ItemMenuDTO;
import com.example.demo.servicios.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemMenuMapper {

    private final ICategoriaService categoriaService;

    @Autowired
    public ItemMenuMapper(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public ItemMenuDTO convertirADTO(ItemMenu itemMenu) {
        if (itemMenu instanceof Bebida bebida) {
            return convertirBebidaADTO(bebida);
        } else if (itemMenu instanceof Comida comida) {
            return convertirComidaADTO(comida);
        }
        throw new IllegalArgumentException("Tipo desconocido de ItemMenu");
    }

    public ItemMenu convertirAEntidad(ItemMenuDTO itemMenuDTO) {
        if (itemMenuDTO instanceof BebidaDTO bebidaDTO) {
            return convertirBebidaAEntidad(bebidaDTO);
        } else if (itemMenuDTO instanceof ComidaDTO comidaDTO) {
            return convertirComidaAEntidad(comidaDTO);
        }
        throw new IllegalArgumentException("Tipo desconocido de ItemMenuDTO");
    }

    private BebidaDTO convertirBebidaADTO(Bebida bebida) {
        BebidaDTO bebidaDTO = new BebidaDTO();
        bebidaDTO.setId(bebida.getId());
        bebidaDTO.setNombre(bebida.getNombre());
        bebidaDTO.setDescripcion(bebida.getDescripcion());
        bebidaDTO.setPrecio(bebida.getPrecio());
        bebidaDTO.setPeso(bebida.getPeso());
        bebidaDTO.setGraduacionAlcoholica(bebida.getGraduacionAlcoholica());
        bebidaDTO.setTamanio(bebida.getTamanio());
        bebidaDTO.setCategoria(convertirCategoriaADTO(bebida.getCategoria())); // Conversión de categoría
        return bebidaDTO;
    }

    private ComidaDTO convertirComidaADTO(Comida comida) {
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comida.getId());
        comidaDTO.setNombre(comida.getNombre());
        comidaDTO.setDescripcion(comida.getDescripcion());
        comidaDTO.setPrecio(comida.getPrecio());
        comidaDTO.setPeso(comida.getPeso());
        comidaDTO.setCalorias(comida.getCalorias());
        comidaDTO.setAptoVegano(comida.isAptoVegano());
        comidaDTO.setAptoCeliaco(comida.isAptoCeliaco());
        comidaDTO.setPesoSinEnvase(comida.getPesoSinEnvase());
        comidaDTO.setCategoria(convertirCategoriaADTO(comida.getCategoria())); // Conversión de categoría
        return comidaDTO;
    }

    private Bebida convertirBebidaAEntidad(BebidaDTO bebidaDTO) {
        Bebida bebida = new Bebida();
        bebida.setId(bebidaDTO.getId());
        bebida.setNombre(bebidaDTO.getNombre());
        bebida.setDescripcion(bebidaDTO.getDescripcion());
        bebida.setPrecio(bebidaDTO.getPrecio());
        bebida.setPeso(bebidaDTO.getPeso());
        bebida.setGraduacionAlcoholica(bebidaDTO.getGraduacionAlcoholica());
        bebida.setTamanio(bebidaDTO.getTamanio());
            bebida.setCategoria(buscarOCrearCategoria(bebidaDTO.getCategoria(),"Bebida")); // Buscar o usar categoría existente
        return bebida;
    }

    private Comida convertirComidaAEntidad(ComidaDTO comidaDTO) {
        Comida comida = new Comida();
        comida.setId(comidaDTO.getId());
        comida.setNombre(comidaDTO.getNombre());
        comida.setDescripcion(comidaDTO.getDescripcion());
        comida.setPrecio(comidaDTO.getPrecio());
        comida.setPeso(comidaDTO.getPeso());
        comida.setCalorias(comidaDTO.getCalorias());
        comida.setAptoVegano(comidaDTO.isAptoVegano());
        comida.setAptoCeliaco(comidaDTO.isAptoCeliaco());
        comida.setPesoSinEnvase(comidaDTO.getPesoSinEnvase());
        comida.setCategoria(buscarOCrearCategoria(comidaDTO.getCategoria(),"Comida")); // Buscar o usar categoría existente
        return comida;
    }

    private CategoriaDTO convertirCategoriaADTO(Categoria categoria) {
        if (categoria == null) return null;
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getDescripcion(),
                categoria.getTipoItem()
        );
    }

    private Categoria buscarOCrearCategoria(CategoriaDTO categoriaDTO, String tipoItemItemMenu) {
        if (categoriaDTO == null || categoriaDTO.getId() == 0) {
            throw new IllegalArgumentException("El DTO de categoría no puede ser nulo y debe tener un ID válido");
        }

        return categoriaService.buscarCategoriaPorId(categoriaDTO.getId())
                .map(categoria -> {
                    // Validar que el tipoItem de la categoría coincida con el tipoItem del ItemMenu
                    if (!categoria.getTipoItem().equalsIgnoreCase(tipoItemItemMenu)) {
                        throw new IllegalArgumentException(
                                "El tipoItem de la categoría existente no coincide con el del ItemMenu. "
                                        + "Esperado: " + tipoItemItemMenu
                                        + ", Actual: " + categoria.getTipoItem()
                        );
                    }
                    return categoria; // Reutilizar la categoría existente
                })
                .orElseGet(() -> {
                    // Crear una nueva categoría si no existe
                    if (categoriaDTO.getDescripcion() == null || categoriaDTO.getTipoItem() == null) {
                        throw new IllegalArgumentException("Para crear una nueva categoría, se requieren 'descripcion' y 'tipoItem'");
                    }
                    // Validar que el tipoItem proporcionado coincida con el del ItemMenu
                    if (!categoriaDTO.getTipoItem().equalsIgnoreCase(tipoItemItemMenu)) {
                        throw new IllegalArgumentException(
                                "El tipoItem de la nueva categoría no coincide con el del ItemMenu. "
                                        + "Esperado: " + tipoItemItemMenu
                                        + ", Proporcionado: " + categoriaDTO.getTipoItem()
                        );
                    }
                    Categoria nuevaCategoria = new Categoria();
                    nuevaCategoria.setId(categoriaDTO.getId());
                    nuevaCategoria.setDescripcion(categoriaDTO.getDescripcion());
                    nuevaCategoria.setTipoItem(categoriaDTO.getTipoItem());
                    return nuevaCategoria;
                });
    }



}
