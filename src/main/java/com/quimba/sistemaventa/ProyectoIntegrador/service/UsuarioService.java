package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.UsuarioRepository;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    //obtener la lista de usuarios
    public List<Usuario> list(){
        return usuarioRepository.findAll();
    }

    //obtener categoria por id
    public Optional<Usuario> getOne(Integer id){
        return usuarioRepository.findById(id);
    }

    //obtener el nombre de usuario
    public Optional<Usuario> getByNombreUsuario(String nombre){
        return usuarioRepository.findByNombreUsuario(nombre);
    }

    //guardar usuario
    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    //eliminar por id
    public void delete(Integer id){
        usuarioRepository.deleteById(id);
    }

    //existe por id
    public boolean existsById(Integer id){
        return usuarioRepository.existsById(id);
    }

    //existe por nombreUusario
    public boolean existsByNombreUsuario(String nombre){
        return usuarioRepository.existsByNombreUsuario(nombre);
    }

    public Optional <Usuario> findByNombreUsuarioAndPassword(String nombreUsuario, String password){

       return usuarioRepository.findByNombreUsuarioAndPassword(nombreUsuario, password);
    }
}
