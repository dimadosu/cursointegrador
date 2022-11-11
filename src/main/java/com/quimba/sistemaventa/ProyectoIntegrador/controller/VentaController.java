package com.quimba.sistemaventa.ProyectoIntegrador.controller;


import com.quimba.sistemaventa.ProyectoIntegrador.modelo.*;
import com.quimba.sistemaventa.ProyectoIntegrador.service.*;
import com.quimba.sistemaventa.ProyectoIntegrador.util.reportes.VentaExporteEXCEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/mesa")
    public String irAMesas(){
        return "/venta/mesa";
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
    public String hacerVenta(Model modelo){
        Venta venta = new Venta();
        modelo.addAttribute("venta",venta);
        return "/venta/venta";
        //pasamos objeto venta para que en el formulario de venta asignemos los atributos y retornemos
    }

    @GetMapping("/detalleVenta")
    public String hacerDetalleVenta(Model modelo){
        Producto producto = new Producto();
        modelo.addAttribute("producto",producto);
        //DetalleVenta detalleVenta = new DetalleVenta();
        //modelo.addAttribute("detalleVenta", detalleVenta);
        return "/venta/detalleVenta";
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

    //cada vez que nos dirigimos a esa pagina, se cargaran las categrias y productos

    /*
    @GetMapping("/buscarCliente")
    public String buscarDatosCliente(@PathVariable("dni") String dni, Model modelo){
        Cliente cliente = clienteService.getByDni(dni).get();
        modelo.addAttribute("cliente", cliente);
        return "/venta/detalleVenta";
    }*/

    //con param indicamos que enviaremos un parametro
    /*
    @GetMapping("/encontrarPlatillos")
    public String listarPlatilloPorCategoria(Model modelo, @Param("palabraClave") String palabraClave){
        List<Producto> listaProductos = productoService.listProductos(palabraClave);
        modelo.addAttribute("listaProductos",listaProductos);
        modelo.addAttribute("palabraClave",palabraClave);
        return null;
    }*/

    /*
    @PostMapping("/agregar")
    public ModelAndView guardarEnTabla(Model modelo, DetalleVenta detalleVenta){
        ModelAndView mv = new ModelAndView();
        detalleVentaService.save(detalleVenta);
        List<DetalleVenta> listaDetalleVenta = detalleVentaService.list();
        modelo.addAttribute("listaDetalleVenta", listaDetalleVenta);
        List<Categoria> listaCategorias = categoriaService.list();
        modelo.addAttribute("listaCategorias", listaCategorias);
        List<Producto> listaProductos = productoService.list();
        modelo.addAttribute("listaProductos", listaProductos);
        mv.setViewName("redirect:/venta/detalleVenta");
        return mv;
    }*/
}
