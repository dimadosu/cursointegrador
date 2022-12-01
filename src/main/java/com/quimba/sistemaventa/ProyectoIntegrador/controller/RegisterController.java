package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Rol;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;
import com.quimba.sistemaventa.ProyectoIntegrador.service.UsuarioService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.impl.EncryptServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EncryptServiceImpl encryptService;

    String pass;

    Rol rol = new Rol();

    @GetMapping("/nuevoUsuario")
    public String nuevoUsuario(Model modelo){
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        return "register";
    }
    @PostMapping("/registrarUsuario")
    public ModelAndView guardarUsuario(Usuario usuario){

        ModelAndView mv = new ModelAndView();

        if(StringUtils.isBlank(usuario.getNombre()) || StringUtils.isBlank(usuario.getApellidoPaterno())
           || StringUtils.isBlank(usuario.getApellidoMaterno()) || StringUtils.isBlank(usuario.getDni())
                || StringUtils.isBlank(usuario.getPassword()) || StringUtils.isBlank(usuario.getNombreUsuario())){
            mv.setViewName("register");
            mv.addObject("error", "Tiene campos vacios!, complételos");
            return mv;
        }

        if(usuarioService.existsByNombreUsuario(usuario.getNombreUsuario())){
            mv.setViewName("register");
            mv.addObject("error", "Este nombre de usuario ya existe!");
            return mv;
        }
        rol.setId(2);
        pass = encryptService.encryptPassword(usuario.getPassword());
        usuario.setPassword(pass);
        usuario.setRol(rol);
        usuarioService.save(usuario);
        mv.setViewName("login");
        return mv;
    }
}
