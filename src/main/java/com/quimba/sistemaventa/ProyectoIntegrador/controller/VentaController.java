package com.quimba.sistemaventa.ProyectoIntegrador.controller;


import com.quimba.sistemaventa.ProyectoIntegrador.modelo.*;
import com.quimba.sistemaventa.ProyectoIntegrador.service.*;
import com.quimba.sistemaventa.ProyectoIntegrador.util.reportes.VentaExporteEXCEL;
import com.quimba.sistemaventa.ProyectoIntegrador.util.reportes.VentaExporterPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/mesa")
    public ModelAndView mostrarVentanaMesas(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/venta/mesa");
        return mv;
    }

    //LISTO-TERMINADO
    @PostMapping("/guardarVenta")
    public ModelAndView registrarVenta(Venta venta){
        ModelAndView mv = new ModelAndView();
        ventaService.save(venta);
        mv.setViewName("redirect:/venta/venta");
        return mv;
    }

    //LISTO-TERMIANDO
    @GetMapping("/venta")
    public ModelAndView hacerVenta(Model modelo){
        ModelAndView mv = new ModelAndView();
        Venta venta = new Venta();
        mv.setViewName("/venta/venta");
        modelo.addAttribute("venta",venta);
        return mv;
        //pasamos objeto venta para que en el formulario de venta asignemos los atributos y retornemos
    }

    @GetMapping("/detalleVenta")
    public ModelAndView hacerDetalleVenta(Model modelo){
        //Integer id = ventaService.findById(); //obtenemos el id de la venta ingresada
        ModelAndView mv = new ModelAndView();
        Producto producto = new Producto();
        mv.setViewName("/venta/detalleVenta");
        modelo.addAttribute("producto",producto);
        //modelo.addAttribute("id",id);
        return mv;
    }

    //muestra las ventas(general)
    @GetMapping("lista")
    public ModelAndView listarVentas(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/venta/ventasGenerales");
        List<Venta> ventas = ventaService.list();
        mv.addObject("ventas",ventas);
        return mv;
    }
    //elimina las ventas(tbventas)

    @GetMapping("/eliminarTodo")
    public ModelAndView borrarTodasLasVentas(){
        ventaService.delete();
        return new ModelAndView("redirect:/venta/ventaGenerales");
    }

    @GetMapping("/exportarEXCEL")
    public void exportarVentasExcel(HttpServletResponse response)throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormat.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=ReporteDeVentas_" + fechaActual + ".xlsx"; //formato de excel

        response.setHeader(cabecera,valor);

        List<Venta> listaVentas = ventaService.list();
        VentaExporteEXCEL exporteEXCEL = new VentaExporteEXCEL(listaVentas);
        exporteEXCEL.exportar(response);
    }

    /*
    @GetMapping("/exportarVenta/")
    public ResponseEntity<Resource> exportReportePdf(@RequestParam Integer idClie, @RequestParam Integer idVenta){
            return this.ventaService.exportReporte(idClie, idVenta);
    }*/


    @GetMapping("/exportarVentaPdf/{id}")
    public void imprimir(@PathVariable("id") Integer id,HttpServletResponse response ) throws IOException{
        response.setContentType("application/pdf");
        Venta venta = ventaService.getOne(id).get();

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Venta_" + id + ".pdf"; //formato de pdf

        response.setHeader(cabecera,valor);

        List<DetalleVenta> detalleVentaList = ventaService.getOne(id).get().getDetalleVentaList();

       VentaExporterPDF exporterPDF = new VentaExporterPDF(detalleVentaList, venta.getId(), venta);

       exporterPDF.exportar(response);
    }
}
