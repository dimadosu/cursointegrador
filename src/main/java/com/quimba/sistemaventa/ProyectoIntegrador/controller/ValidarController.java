package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;
import com.quimba.sistemaventa.ProyectoIntegrador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/validar")
public class ValidarController {

    @Autowired
    private UsuarioService usuarioService;

    Usuario usuario = new Usuario();

    @PostMapping("/validarDatos")
    public ModelAndView validar(@RequestParam String nombreUsuario, @RequestParam String password){
        ModelAndView mv = new ModelAndView();
        usuario= usuarioService.validar(nombreUsuario,password);
        if(usuario.getRol().getId()==1){
            mv.addObject("usuario",usuario);
            mv.setViewName("Principal");
        }
        if(usuario.getRol().getId()==2){
            mv.setViewName("login");
        }
        return mv;
    }
}
