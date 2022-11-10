package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.DetalleVenta;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Integer> {


}
