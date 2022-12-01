package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.DetalleVenta;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Integer> {


    /*
    @Query(
            "SELECT DP FROM DetalleVenta DP WHERE DP.venta.id=:idV"
    )
    Iterator<DetalleVenta> findByVenta(Integer idV);*/

}
