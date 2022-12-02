package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
