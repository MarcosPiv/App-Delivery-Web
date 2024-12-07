package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.patrones.observer.Observable;
import com.example.demo.patrones.observer.Observer;
import com.example.demo.patrones.strategy.PagarMercadoPago;
import com.example.demo.patrones.strategy.PagarTransferencia;
import com.example.demo.patrones.strategy.PagoStrategy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Entity
public class Pedido extends Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    @SequenceGenerator(name = "pedido_seq", sequenceName = "pedido_sequence", allocationSize = 1)
    private int id;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor restaurante;
    private double precioTotal;
    @OneToMany(mappedBy = "pedido", targetEntity = DetallePedido.class, cascade = CascadeType.ALL)
    private List<DetallePedido> detallesPedido;
    private Estado estado;
    @Transient // indica que no se persiste en la base de datos
    private PagoStrategy tipoDePago;
    @Transient
    private ArrayList<Observer> observers = new ArrayList<>();

    public Pedido() {
    this.detallesPedido = new ArrayList<>();
    }

    public Pedido(int id, Cliente cliente, Vendedor restaurante, double precioTotal, ArrayList<DetallePedido> detallesPedido, Estado estado) {
        this.id = id;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.precioTotal = precioTotal;
        this.detallesPedido = detallesPedido;
        this.estado = estado;
    }
    public PagoStrategy getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(PagoStrategy tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public void pagarMercadoPago(String alias,Double importeTotal){
        tipoDePago = new PagarMercadoPago(alias,importeTotal);
    }

    public void pagarTransferencia(int cbu,String cuit,Double importeTotal){
        tipoDePago = new PagarTransferencia(cbu,cuit,importeTotal);
    }

    public void agregarDetalle(DetallePedido detalle){
        detallesPedido.add(detalle);
    }

    public void quitarDetalle(DetallePedido detalle){
        detallesPedido.remove(detalle);
    }

    public Vendedor getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Vendedor restaurante) {
        this.restaurante = restaurante;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        notifyObservers();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public ArrayList<DetallePedido> getDetallesPedido() {
        return (ArrayList<DetallePedido>) detallesPedido;
    }

    public void setDetallesPedido(ArrayList<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public double calcularPrecioTotal() {
        double total = 0;
        try {
            for (DetallePedido unDetalle : detallesPedido) {
                if (unDetalle == null) {
                    throw new NullPointerException("DetallePedido es nulo");
                }
                double totalPorDetalle = unDetalle.getCantidad() * unDetalle.getPrecio();
                total = total + totalPorDetalle;
            }
        } catch (NullPointerException e) {
            System.err.println("Se encontró un valor nulo: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.err.println("Error aritmético: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
        }
        return total;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}