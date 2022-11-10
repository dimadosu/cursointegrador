package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Categoria;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //obtener la lista de categorias
    public List<Categoria> list(){
        return categoriaRepository.findAll();
    }

    //obtener una categoria por id
    public Optional<Categoria> getOne(Integer id){
        return categoriaRepository.findById(id);
    }

    //obtener una cat por nombre
    public Optional<Categoria> getByNombre(String nombre){
        return categoriaRepository.findByNombre(nombre);
    }

    //guardar categoria
    public void save(Categoria categoria){
        categoriaRepository.save(categoria);
    }

    //eliminar por id
    public void delete(Integer id){
        categoriaRepository.deleteById(id);
    }

    //existe por id
    public boolean existsById(Integer id){
        return categoriaRepository.existsById(id);
    }

    //existe por nombre
    public boolean existsByNombre(String nombre){
        return categoriaRepository.existsByNombre(nombre);
    }

}
