package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> list(){
        return productoRepository.findAll();
    }

    public Optional<Producto> getOne(Integer id){
        return productoRepository.findById(id);
    }

    public Optional<Producto> getByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public void save(Producto producto){
        productoRepository.save(producto);
    }

    public void delete(Integer id){
        productoRepository.deleteById(id);
    }

    public boolean existsById(Integer id){
        return productoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return productoRepository.existsByNombre(nombre);
    }

    public List<Producto> listProductos(String palabraClave){
        return productoRepository.findProductosPorCategoria(palabraClave);
    }

    public Optional<Producto> findByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

}
