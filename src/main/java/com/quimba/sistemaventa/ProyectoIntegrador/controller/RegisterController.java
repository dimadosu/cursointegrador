package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {


    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrarUsuario")
    public ModelAndView guardarUsuario(){

        return new ModelAndView("login");
    }
}
