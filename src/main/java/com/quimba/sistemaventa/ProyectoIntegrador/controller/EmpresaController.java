package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Empresa;
import com.quimba.sistemaventa.ProyectoIntegrador.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam Integer id, Empresa empresa){
        ModelAndView mv = new ModelAndView();
        Empresa empresaUpdate = empresaService.getOne(id).get(); //obtenemos la empresa a travez del id
        empresaUpdate.setNombre(empresa.getNombre());
        empresaUpdate.setDireccion(empresa.getDireccion());
        empresaUpdate.setRazon(empresa.getRazon());
        empresaUpdate.setRepresentante(empresa.getRepresentante());
        empresaUpdate.setCorreo(empresa.getCorreo());
        empresaUpdate.setTelefono(empresa.getTelefono());
        empresaUpdate.setHorario(empresa.getHorario());
        empresaService.save(empresaUpdate);
        mv.setViewName("home");
        return mv;
    }

}
