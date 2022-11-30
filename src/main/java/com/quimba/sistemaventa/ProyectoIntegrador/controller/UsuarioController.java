package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Rol;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Usuario;
import com.quimba.sistemaventa.ProyectoIntegrador.service.RolService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.UsuarioService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.impl.EncryptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private EncryptServiceImpl encryptService;

    String pass;

    //lista los usuarios en la pagina/ESTA LISTO
    @GetMapping("lista")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/usuario/lista");
        List<Usuario> usuarios = usuarioService.list();
        mv.addObject("usuarios", usuarios);
        return mv;
    }

    //manda a la pagina para crea nuevo usuario//ESTA LISTO
    @GetMapping("nuevo")
    public String nuevo(Model modelo){
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        List<Rol> listaRoles = rolService.list();
        modelo.addAttribute("listaRoles", listaRoles);
        return "/usuario/nuevo";
    }

    //guarda usuario:LISTO
    @PostMapping("/guardar")
    public ModelAndView crearUsuario(Usuario usuario, Model modelo){
        ModelAndView mv = new ModelAndView();
        if(usuarioService.existsByNombreUsuario(usuario.getNombreUsuario())){
            mv.setViewName("usuario/nuevo");
            List<Rol> listaRoles = rolService.list();
            modelo.addAttribute("listaRoles", listaRoles);
            mv.addObject("error", "Este nombre de usuario ya existe");
            return mv;
        }
        pass = encryptService.encryptPassword(usuario.getPassword()); //encriptamos la password
        usuario.setPassword(pass); //establecemos la pass
        usuarioService.save(usuario);
        mv.setViewName("redirect:/usuario/lista");
        return mv;
    }

    //manda a la pagina de editar y completa los campos:ESTA LISTO
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id, Model modelo){
        if(!usuarioService.existsById(id)){ //si no existe el id a editar
            return new ModelAndView("redirect:/usuario/lista");
        }
        List<Rol> listaRoles = rolService.list();
        modelo.addAttribute("listaRoles", listaRoles);
        Usuario usuario = usuarioService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/usuario/editar");
        mv.addObject("usuario", usuario);
        return mv;
    }

    //ESTA LISTO
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam Integer id, Usuario usuario, Model modelo){
        ModelAndView mv = new ModelAndView();
        Usuario usuario1 = usuarioService.getOne(id).get();
        if(usuarioService.existsByNombreUsuario(usuario.getNombreUsuario()) && usuarioService.getByNombreUsuario(usuario.getNombreUsuario()).get().getId() != id){
            mv.setViewName("usuario/editar");
            List<Rol> listaRoles = rolService.list();
            modelo.addAttribute("listaRoles", listaRoles);
            mv.addObject("error", "Este nombre de usuario ya existe");
            return mv;
        }
        usuario1.setNombre(usuario.getNombre());
        usuario1.setApellidoPaterno(usuario.getApellidoPaterno());
        usuario1.setApellidoMaterno(usuario.getApellidoMaterno());
        usuario1.setCelular(usuario.getCelular());
        usuario1.setNombreUsuario(usuario.getNombreUsuario());
        usuario1.setDni(usuario.getDni());
        usuario1.setCorreo(usuario.getCorreo());
        usuario1.setRol(usuario.getRol());
        usuario1.setPassword(encryptService.encryptPassword(usuario.getPassword()));
        usuarioService.save(usuario1);
        return new ModelAndView("redirect:/usuario/lista");
    }

    //ESTA LISTO
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id") Integer id){
        if(usuarioService.existsById(id)){
            usuarioService.delete(id);
            return new ModelAndView("redirect:/usuario/lista");
        }
        return null;
    }



}
