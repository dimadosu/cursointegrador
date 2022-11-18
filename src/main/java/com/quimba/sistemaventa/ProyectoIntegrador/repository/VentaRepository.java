package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Integer> {

    @Query(
        value = "SELECT MAX(id) from ventas",
            nativeQuery = true
    )
    Integer findById();

    /*
    @Query(
        value = "INSERT INTO ventas (total) VALUES (?)",
        nativeQuery = true
    )
    Double saveByTotal();*/
}
