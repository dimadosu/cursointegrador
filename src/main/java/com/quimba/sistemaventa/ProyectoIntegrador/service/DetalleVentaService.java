package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.DetalleVenta;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVenta> list(){
        return detalleVentaRepository.findAll();
    }

    public void save(DetalleVenta detalleVenta){
        detalleVentaRepository.save(detalleVenta);
    }

    public void delete(Integer id){
        detalleVentaRepository.deleteById(id);
    }

}
