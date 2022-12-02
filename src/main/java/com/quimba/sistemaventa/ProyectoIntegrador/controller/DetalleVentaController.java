package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.DetalleVenta;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Producto;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import com.quimba.sistemaventa.ProyectoIntegrador.service.DetalleVentaService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.ProductoService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.VentaService;
import com.quimba.sistemaventa.ProyectoIntegrador.util.reportes.VentaExporterPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
    public ModelAndView mostrarDatoDelPlato(Producto producto, Model modelo){
        ModelAndView mv = new ModelAndView();
        if(!productoService.existsById(producto.getId())){
            mv.setViewName("/venta/detalleVenta");
            modelo.addAttribute("listaDetalles", listaDetalles);
            modelo.addAttribute("total",total);
            modelo.addAttribute("error","platillo no encontrado");
            return mv;
        }
        Producto producto1 = productoService.getOne(producto.getId()).get();
        modelo.addAttribute("listaDetalles", listaDetalles);
        modelo.addAttribute("total",total);
        modelo.addAttribute("producto", producto1);
        mv.setViewName("/venta/detalleVenta");
        return mv;
    }

    @GetMapping("/buscarPlatilloNombre")
    public ModelAndView mostrarDatoDelPlatoNombre(Producto producto, Model modelo){
        ModelAndView mv = new ModelAndView();
        if(!productoService.existsByNombre(producto.getNombre())){
            mv.setViewName("/venta/detalleVenta");
            modelo.addAttribute("listaDetalles", listaDetalles);
            modelo.addAttribute("total",total);
            modelo.addAttribute("error","platillo no encontrado");
            return mv;
        }
        Producto producto1 = productoService.findByNombre(producto.getNombre()).get();
        modelo.addAttribute("listaDetalles", listaDetalles);
        modelo.addAttribute("total",total);
        modelo.addAttribute("producto", producto1);
        mv.setViewName("/venta/detalleVenta");
        return mv;
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
        return new ModelAndView("redirect:/venta/detalleVenta");
    }

    /*
    @GetMapping("/imprimirVenta")
    public void imprimirVenta(){
        VentaExporterPDF exporterPDF = new VentaExporterPDF(listaDetalles);
        exporterPDF.exportarPdfVenta();
        venta = new Venta();
        venta1 = new Venta();
        listaDetalles.clear();
    }*/
}
