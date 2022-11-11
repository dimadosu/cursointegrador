package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import com.quimba.sistemaventa.ProyectoIntegrador.service.DetalleVentaService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/detalleVenta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/procesar")
    public String terminarventa(){
        return "venta/mesa";
    }

    @GetMapping("/buscarPlatillo")
    public String mostrarDatoDelPlato(Producto producto, Model modelo){
        if(!productoService.existsById(producto.getId())){
            modelo.addAttribute("error","platillo no encontrado");
            return "/venta/detalleVenta";
        }
        Producto producto1 = productoService.getOne(producto.getId()).get();
        modelo.addAttribute("producto", producto1);
        return "/venta/detalleVenta";
    }

    @GetMapping("")
    public ModelAndView guardar(Venta venta, Model modelo){
        ModelAndView mv =  new ModelAndView();
        return  mv;
    }

}
