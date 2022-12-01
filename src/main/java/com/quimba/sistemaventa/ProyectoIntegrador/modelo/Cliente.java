package com.quimba.sistemaventa.ProyectoIntegrador.modelo;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellidoPaterno;

    @Column(length = 100)
    private String apellidoMaterno;

    @Column(length = 15)
    private String dni;

    @Column(length = 15)
    private String celular;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /*
    public String getnombreCliente(){
        return this.nombre!=null && this.apellidoPaterno!=null && this.apellidoMaterno!=null?
                this.nombre+" " + this.apellidoPaterno+ " " + this.apellidoPaterno: "-----";
    }*/
}
