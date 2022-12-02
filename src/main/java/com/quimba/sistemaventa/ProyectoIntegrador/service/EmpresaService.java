package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Empresa;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Optional<Empresa> getOne(Integer id){
        return empresaRepository.findById(id);
    }


    public void save(Empresa empresa){
        empresaRepository.save(empresa);
    }
}
