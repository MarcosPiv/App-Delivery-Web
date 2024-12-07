package com.example.demo.mappers;

import com.example.demo.dto.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PedidoMapper {

    private final ItemMenuMapper itemMenuMapper;
    private final ClienteMapper clienteMapper;
    private final VendedorMapper vendedorMapper;

    @Autowired
    public PedidoMapper(ItemMenuMapper itemMenuMapper, ClienteMapper clienteMapper, VendedorMapper vendedorMapper) {
        this.itemMenuMapper = itemMenuMapper;
        this.clienteMapper = clienteMapper;
        this.vendedorMapper = vendedorMapper;
    }

    public PedidoDTO convertirADTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setCliente(clienteMapper.convertirADTO(pedido.getCliente()));
        dto.setVendedor(vendedorMapper.convertirADTO(pedido.getRestaurante()));
        dto.setPrecioTotal(pedido.getPrecioTotal());
        // Se pasa el PedidoDTO recién creado a la función de mapeo de detalles
        dto.setDetallesPedido(convertirListaDetallesADTO(pedido.getDetallesPedido(), dto));
        dto.setEstado(pedido.getEstado());

        return dto;
    }

    public Pedido convertirAEntidad(PedidoDTO dto) {
        if (dto == null) {
            return null;
        }

        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        pedido.setCliente(clienteMapper.convertirAEntidad(dto.getCliente()));
        pedido.setRestaurante(vendedorMapper.convertirAEntidad(dto.getVendedor()));
        pedido.setPrecioTotal(dto.getPrecioTotal());
        pedido.setDetallesPedido(convertirListaDetallesAEntidad(dto.getDetallesPedido(), pedido));
        pedido.setEstado(dto.getEstado() != null ? dto.getEstado() : Estado.PENDIENTE);

        return pedido;
    }
    // Ahora convertimos la lista de detalles pasando el PedidoDTO para setearlo en cada detalle
    private List<DetallePedidoDTO> convertirListaDetallesADTO(List<DetallePedido> detalles, PedidoDTO pedidoDTO) {
        if (detalles == null) return new ArrayList<>();
        List<DetallePedidoDTO> listaDTO = new ArrayList<>();
        for (DetallePedido d : detalles) {
            DetallePedidoDTO dto = new DetallePedidoDTO();
            dto.setId(d.getId());
            dto.setCantidad(d.getCantidad());
            dto.setPrecio(d.getPrecio());
            // Convertimos el ItemMenu asociado a DTO
            if (d.getItem() != null) {
                ItemMenuDTO itemDTO = itemMenuMapper.convertirADTO(d.getItem());
                dto.setItemMenu(itemDTO);
            }
            // Seteamos el pedidoDTO para evitar recursión (ya tenemos el pedidoDTO principal creado)
            dto.setPedido(pedidoDTO);
            listaDTO.add(dto);
        }
        return listaDTO;
    }
    private ArrayList<DetallePedido> convertirListaDetallesAEntidad(List<DetallePedidoDTO> detallesDTO, Pedido pedido) {
        if (detallesDTO == null || detallesDTO.isEmpty()) {
            throw new IllegalArgumentException("La lista de detalles no puede ser nula o vacía");
        }

        ArrayList<DetallePedido> lista = new ArrayList<>();
        for (DetallePedidoDTO dto : detallesDTO) {
            // Validar que el DTO tiene la información necesaria
            if (dto.getId() == 0) {
                throw new IllegalArgumentException("El ID del detalle de pedido no puede ser 0");
            }

            DetallePedido d = new DetallePedido();
            d.setId(dto.getId());
            d.setPedido(pedido); // Establecer el pedido padre conocido
            d.setCantidad(dto.getCantidad());
            d.setPrecio(dto.getPrecio());

            // Validar y mapear el itemMenu
            if (dto.getItemMenu() != null) {
                ItemMenu item = itemMenuMapper.convertirAEntidad(dto.getItemMenu());
                if (item == null) {
                    throw new IllegalArgumentException("No se pudo mapear el ItemMenu con el ID proporcionado");
                }
                d.setItem(item);
            } else {
                throw new IllegalArgumentException("El ItemMenu no puede ser nulo en el DetallePedido");
            }

            // Agregar el detalle a la lista
            lista.add(d);
        }

        return lista;
    }

}
