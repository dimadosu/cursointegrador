package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Empresa;
import com.quimba.sistemaventa.ProyectoIntegrador.service.EmpresaService;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private  EmpresaService empresaService;

    @GetMapping(value = {"/", "index"})
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @GetMapping("home")
    public String home(Model modelo) {
        Empresa empresa = empresaService.getOne(1).get();
        modelo.addAttribute("empresa", empresa);
        return "home";
    }

    @GetMapping("/forbidden")
    public String forbidden(){
        return "forbidden";
    }


}
