package com.quimba.sistemaventa.ProyectoIntegrador.repository;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    boolean existsByNombreUsuario(String nombreUsuario);

    @Query(
            value = "SELECT * FROM usuarios where nombre_usuario LIKE %?1% and password LIKE%?2%",
            nativeQuery = true
    )
    public Usuario findByNombreUsuarioAndPassword(@Param("nombreUsuario") String nombreUsuario, @Param("password") String password);
}
