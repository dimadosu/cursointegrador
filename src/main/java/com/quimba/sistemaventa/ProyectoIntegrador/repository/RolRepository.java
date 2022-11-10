package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {

    Optional<Rol> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
