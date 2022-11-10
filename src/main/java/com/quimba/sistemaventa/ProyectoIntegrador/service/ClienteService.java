package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Cliente;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> list(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getOne(Integer id){
          return  clienteRepository.findById(id);
    }

    public void save(Cliente cliente){
        clienteRepository.save(cliente);
    }

    public void delete(Integer id){
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> getByDni(String dni){
        return clienteRepository.findByDni(dni);
    }

}
