package com.quimba.sistemaventa.ProyectoIntegrador.modelo;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @PrePersist
    public void Prepersit(){
        this.fecha = new Date();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @OneToMany(mappedBy = "venta",cascade = CascadeType.ALL)
    //@JoinColumn(name = "venta_id")
    private List<DetalleVenta> detalleVentaList;

    private Double total;

    public Venta (){

    }
    /*
    public Venta(){
        this.detalleVentaList = new ArrayList<>();
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
    }

    //calculando el importe neto del pedido o venta

    /*public Double getTotal(){
        Double total=0.0;
        for (DetalleVenta detalleVenta: this.detalleVentaList) {
            total = total + detalleVenta.getImporte();
        }
        return total;
    }*/

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
