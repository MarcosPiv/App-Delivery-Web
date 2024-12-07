package com.example.demo.servicios;

import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Estado;
import com.example.demo.model.Pedido;
import com.example.demo.model.DetallePedido;
import com.example.demo.model.ItemMenu;
import com.example.demo.repositorio.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final IitemMenuService itemMenuService;
    private final IDetallePedidoService detallePedidoService;
    private final ICategoriaService categoriaService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, IitemMenuService itemMenuService, IDetallePedidoService detallePedidoService, ICategoriaService categoriaService) {
        this.pedidoRepository = pedidoRepository;
        this.itemMenuService = itemMenuService;
        this.detallePedidoService = detallePedidoService;
        this.categoriaService = categoriaService;
    }


    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido crearPedido(Pedido pedido) {
        if (pedidoRepository.existsById(pedido.getId())) {
            throw new ResourceAlreadyExistsException("No se puede crear. El pedido con ID " + pedido.getId() + " ya existe.");
        }

        // Guardamos los detalles del pedido
        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            // Verificamos si el ItemMenu existe y si no lo creamos
            ItemMenu itemMenu = detalle.getItem();
            if (itemMenu != null) {
                if (!itemMenuService.existeItemMenu(itemMenu.getId())) {
                    // Verificamos si la categoría del ItemMenu existe y si no la creamos
                    if (!categoriaService.existeCategoria(itemMenu.getCategoria().getId())) {
                        categoriaService.crearCategoria(itemMenu.getCategoria());
                    }
                    itemMenuService.crearItemMenu(itemMenu);
                }
            }
            // Asignamos el pedido al detalle y lo guardamos
            detalle.setPedido(pedido);
            detallePedidoService.crearDetallePedido(detalle);
        }


        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido buscarPedidoPorId(int id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con ID " + id + " no existe."));
    }

    @Override
    public boolean existePedido(int id) {
        return pedidoRepository.existsById(id);
    }

    @Override
    public Pedido actualizarPedido(Pedido pedido) {
        // Aseguramos que el pedido exista antes de actualizar
        if (!existePedido(pedido.getId())) {
            throw new ResourceNotFoundException("El pedido con ID " + pedido.getId() + " no existe.");
        }

        // Actualizamos los detalles del pedido
        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            if (detalle.getItem() != null) {
                // Verificamos si el ItemMenu existe y si no lo creamos
                if(!itemMenuService.existeItemMenu(detalle.getItem().getId())) {
                    // Verificamos si la categoría del ItemMenu existe y si no la creamos
                    if (!categoriaService.existeCategoria(detalle.getItem().getCategoria().getId())) {
                        categoriaService.crearCategoria(detalle.getItem().getCategoria());
                    }
                    itemMenuService.crearItemMenu(detalle.getItem());
                }
            }
        }

        return pedidoRepository.save(pedido);
    }

    @Override
    public void eliminarPedido(int id) {
        if (!existePedido(id)) {
            throw new ResourceNotFoundException("El pedido con ID " + id + " no existe.");
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    public void cambiarEstadoPedido(int id, String nuevoEstado) {
        Pedido pedido = buscarPedidoPorId(id);

        // Intentar parsear el enum
        Estado estado;
        try {
            estado = Estado.valueOf(nuevoEstado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + nuevoEstado);
        }

        pedido.setEstado(estado);
        pedidoRepository.save(pedido);
    }
}

