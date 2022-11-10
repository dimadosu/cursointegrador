package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Categoria;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import com.quimba.sistemaventa.ProyectoIntegrador.service.CategoriaService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    //lista los productos en la tabla//ESTA LISTO
    @GetMapping("lista")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/producto/lista");
        List<Producto> productos = productoService.list();
        mv.addObject("productos", productos);
        return mv;
    }

    //manda a la pagina para crear productos//ESTALISTO
    @GetMapping("nuevo")
    public String nuevo(Model modelo){
        Producto producto = new Producto();
        modelo.addAttribute("producto",producto);
        List<Categoria> listaCategorias = categoriaService.list();
        modelo.addAttribute("listaCategoria", listaCategorias);
        return "/producto/nuevo";
    }

    //ESTA LISTO
    @PostMapping("/guardar")
    public ModelAndView crearProducto(Producto producto, Model modelo){
        ModelAndView mv = new ModelAndView();
        if(productoService.existsByNombre(producto.getNombre())){
            mv.setViewName("producto/nuevo");
            List<Categoria> listaCategorias = categoriaService.list();
            modelo.addAttribute("listaCategoria", listaCategorias);
            mv.addObject("error","el producto ya existe");
            return mv;
        }
        productoService.save(producto);
        mv.setViewName("redirect:/producto/lista");
        return mv;
    }

    //manda a la pagina para editar y completa los campos//ESTA LISTO
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id, Model modelo){
        if(!productoService.existsById(id)){
            return new ModelAndView("redirect:/producto/lista");
        }
        List<Categoria> listaCategorias = categoriaService.list();
        modelo.addAttribute("listaCategoria", listaCategorias);
        Producto producto = productoService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/producto/editar");
        mv.addObject("producto", producto);
        return mv;
    }

    //ESTA LISTO
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam Integer id ,Producto producto, Model modelo){
        ModelAndView mv = new ModelAndView();
        Producto producto1 = productoService.getOne(id).get();
        if(productoService.existsByNombre(producto.getNombre()) && productoService.getByNombre(producto.getNombre()).get().getId() != id){
            mv.setViewName("producto/editar");
            List<Categoria> listaCategorias = categoriaService.list();
            modelo.addAttribute("listaCategoria", listaCategorias);
            mv.addObject("error","el producto ya existe");
            return mv;
        }
        producto1.setNombre(producto.getNombre());
        producto1.setPrecio(producto.getPrecio());
        producto1.setCategoria(producto.getCategoria());
        productoService.save(producto1);
        return new ModelAndView("redirect:/producto/lista");
    }

    //elimina productos//-ESTA LISTO
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id") Integer id){
        if(productoService.existsById(id)){
            productoService.delete(id);
            return new ModelAndView("redirect:/producto/lista");
        }
        return null;
    }
}
