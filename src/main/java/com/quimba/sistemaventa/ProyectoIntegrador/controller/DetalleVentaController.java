package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.DetalleVenta;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import com.quimba.sistemaventa.ProyectoIntegrador.service.DetalleVentaService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.ProductoService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.print.attribute.standard.PresentationDirection;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/detalleVenta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/procesar")
    public String terminarventa(){
        return "venta/mesa";
    }


    Venta venta = new Venta();

    //almacenar detalles de venta
    List<DetalleVenta> listaDetalles = new ArrayList<DetalleVenta>();


    @GetMapping("/buscarPlatillo")
    public String mostrarDatoDelPlato(Producto producto, Model modelo){
        if(!productoService.existsById(producto.getId())){
            modelo.addAttribute("listaDetalles", listaDetalles);
            modelo.addAttribute("total",total);
            modelo.addAttribute("error","platillo no encontrado");
            return "/venta/detalleVenta";
        }
        Producto producto1 = productoService.getOne(producto.getId()).get();
        modelo.addAttribute("listaDetalles", listaDetalles);
        modelo.addAttribute("total",total);
        modelo.addAttribute("producto", producto1);
        return "/venta/detalleVenta";
    }

    Integer item=0;
    Double total;

    @PostMapping("/agregar")
    public ModelAndView agregar( @RequestParam Integer id,@RequestParam Integer cantidad, Model modelo, Producto producto){
        Producto productoObtenido =  productoService.getOne(id).get();
        DetalleVenta detalleVenta = new DetalleVenta();
        total = 0.0;

        item = item +1;
        detalleVenta.setId(item);
        detalleVenta.setCantidad(cantidad);
        detalleVenta.setPrecio(producto.getPrecio());
        detalleVenta.setProducto(productoObtenido);
        detalleVenta.setImporte(producto.getPrecio()*cantidad);
        listaDetalles.add(detalleVenta);//añadiendo una detalle de venta a la lista

        for (int i =0; i<listaDetalles.size(); i++){
             total= total+listaDetalles.get(i).getImporte();
        }

        modelo.addAttribute("listaDetalles", listaDetalles);
        modelo.addAttribute("total",total);
        return  new ModelAndView("/venta/detalleVenta");
    }

    Venta venta1 = new Venta();
    @PostMapping("/procesar")
    public ModelAndView procesarVenta(){
        Integer id = ventaService.findById(); //obtenemos el id de la venta ingresada
        venta.setId(id);
        //venta.setTotal(total);
        venta1 = ventaService.getOne(id).get();
        venta1.setTotal(total);
        ventaService.save(venta1);
        //mandando la lista de detalles a la tabla
        for (DetalleVenta detalleVenta: listaDetalles){
            detalleVenta.setVenta(venta);
            detalleVentaService.save(detalleVenta);
        }
        venta = new Venta();
        venta1 = new Venta();
        listaDetalles.clear();
        return new ModelAndView("redirect:/venta/mesa");
    }
}
