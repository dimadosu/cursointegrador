package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;
import com.quimba.sistemaventa.ProyectoIntegrador.service.UsuarioService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.impl.EncryptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/validar")
public class ValidarController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    EncryptServiceImpl encryptService;

    Usuario  usuario = new Usuario();

    @PostMapping("/validarDatos")
    public ModelAndView validar(@RequestParam("nombreUsuario") String nombreUsuario, @RequestParam("password") String password){
        ModelAndView mv = new ModelAndView();

        if(!usuarioService.existsByNombreUsuario(nombreUsuario)){
            mv.setViewName("login");
            mv.addObject("error","usuario o password incorrecto");
            return mv;
        }
        usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
        //usuario = usuarioService.findByNombreUsuarioAndPassword(nombreUsuario,encryptService.encryptPassword(password)).get();
        if(usuarioService.existsByNombreUsuario(nombreUsuario) && encryptService.verifyPassword(password,usuario)){
            if(usuario.getRol().getId()==1){
                mv.addObject("usuario",usuario);
                mv.setViewName("Principal");
            }
            if(usuario.getRol().getId()==2){
                mv.addObject("usuario",usuario);
                mv.setViewName("PrincipalUser");
            }
        }
        return mv;
    }

    @GetMapping("/cerrar")
    public ModelAndView cerrarSession(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
