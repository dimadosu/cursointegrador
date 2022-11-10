package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Categoria;
import com.quimba.sistemaventa.ProyectoIntegrador.service.CategoriaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("lista")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/categoria/lista");
        List<Categoria> categorias = categoriaService.list();
        mv.addObject("categorias", categorias);
        return mv;
    }

    //manda a la pagina para crear una nueva categoria
    @GetMapping("nuevo")
    public String nuevo(){
        return "/categoria/nuevo";
    }

    @PostMapping("/guardar")
    public ModelAndView crear(@RequestParam String nombre){
        ModelAndView mv = new ModelAndView();
        //validaciones
        if(StringUtils.isBlank(nombre)){
            mv.setViewName("categoria/nuevo");
            mv.addObject("error", "el nombre no puede estar vacio");
            return  mv;
        }

        if(categoriaService.existsByNombre(nombre)){
            mv.setViewName("categoria/nuevo");
            mv.addObject("error", "el nombre ya existe");
            return  mv;
        }
        //mandamos el objeto
        Categoria categoria = new Categoria(nombre);
        categoriaService.save(categoria);
        mv.setViewName("redirect:/categoria/lista");
        return mv;
    }

    //manda a la pagina para editar y completa los campos
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id){
        if(!categoriaService.existsById(id)) //si no existe el id para editar, mandar a esta vista
            return new ModelAndView("redirect:/categoria/lista");
        Categoria categoria = categoriaService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/categoria/editar"); //existe la categoria, mandamos a editar
        mv.addObject("categoria", categoria);
        return  mv;
    }

    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam Integer id, @RequestParam String nombre){
        if(!categoriaService.existsById(id))
            return new ModelAndView("redirect:/categoria/lista");
        ModelAndView mv = new ModelAndView();
        Categoria categoria = categoriaService.getOne(id).get();
        if(StringUtils.isBlank(nombre)){ //validamos que el nombre no este vacio
            mv.setViewName("categoria/editar");
            mv.addObject("categoria", categoria);
            mv.addObject("error","el nombre no puede estar vacio");
            return mv;
        }

        //validamos si el nombre ya esta registrado
        if(categoriaService.existsByNombre(nombre) && categoriaService.getByNombre(nombre).get().getId() != id){
            mv.setViewName("categoria/editar");
            mv.addObject("error", "la categoria ya existe");
            mv.addObject("categoria", categoria);
            return mv;
        }

        categoria.setNombre(nombre);
        categoriaService.save(categoria);
        return new ModelAndView("redirect:/categoria/lista");
    }

    //elimina un registro y manda a la lista de todo
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id") Integer id){
        if(categoriaService.existsById(id)){
            categoriaService.delete(id);
            return  new ModelAndView("redirect:/categoria/lista");
        }
        return null;
    }
}
