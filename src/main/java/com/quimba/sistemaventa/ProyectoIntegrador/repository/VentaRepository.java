package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Integer> {

    @Query(
        value = "SELECT MAX(id) from ventas",
            nativeQuery = true
    )
    Integer findById();


    /*
    @Query("SELECT V FROM Venta V WHERE V.id=:idVenta AND V.cliente.id=:idCli")
    Optional<Venta> findByIdAndClienteId(Integer idCli, Integer idVenta);*/

}
