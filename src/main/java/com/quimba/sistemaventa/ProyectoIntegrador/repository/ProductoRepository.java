package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {



    Optional<Producto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);

    @Query("SELECT p from Producto p " +
            " INNER JOIN Categoria c on p.categoria.id= c.id " +
            " WHERE c.nombre = :palabraClave")
    List<Producto> findProductosPorCategoria(@Param("palabraClave") String palabraClave);
}
