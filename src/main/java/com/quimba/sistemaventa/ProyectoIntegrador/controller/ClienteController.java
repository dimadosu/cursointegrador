package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Cliente;
import com.quimba.sistemaventa.ProyectoIntegrador.service.ClienteService;
import com.quimba.sistemaventa.ProyectoIntegrador.util.reportes.ClienteExporteEXCEL;
import com.quimba.sistemaventa.ProyectoIntegrador.util.reportes.ClienteExporterPDF;
import org.springframework.beans.factory.annotation.Autowired;
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


@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //LISTO
    @GetMapping("lista")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cliente/lista");
        List<Cliente> listaClientes = clienteService.list();
        mv.addObject("clientes", listaClientes);
        return mv;
    }

    //LISTO
    @GetMapping("nuevo")
    public String nuevo(Model modelo){
        Cliente cliente = new Cliente();
        modelo.addAttribute("cliente", cliente);
        return "/cliente/nuevo";
    }

    //LISTO
    @PostMapping("/guardar")
    public ModelAndView crearCliente(Cliente cliente){
        ModelAndView mv = new ModelAndView();
        clienteService.save(cliente);
        mv.setViewName("redirect:/cliente/lista");
        return mv;
    }

    //LISTO
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id, Model modelo){
        Cliente cliente = clienteService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/cliente/editar");
        mv.addObject("cliente", cliente);
        return  mv;
    }

    //LISTO
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam Integer id, Cliente cliente, Model modelo){
        Cliente cliente1 = clienteService.getOne(id).get();
        cliente1.setNombre(cliente.getNombre());
        cliente1.setApellidoPaterno(cliente.getApellidoPaterno());
        cliente1.setApellidoMaterno(cliente.getApellidoMaterno());
        cliente1.setDni(cliente.getDni());
        cliente1.setCelular(cliente.getCelular());
        cliente1.setUsuario(cliente.getUsuario());
        clienteService.save(cliente1);
        return new ModelAndView("redirect:/cliente/lista");
    }

    //LISTO
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id")Integer id){
        clienteService.delete(id);
        return new ModelAndView("redirect:/cliente/lista");
    }

    //LISTO
    @GetMapping("/exportarPDF")
    public void exportarListaClientesPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormat.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Clientes_" + fechaActual + ".pdf"; //formato de pdf

        response.setHeader(cabecera,valor);

        List<Cliente> clienteLista = clienteService.list(); //traemos la lista de clientes

        ClienteExporterPDF exporterPDF = new ClienteExporterPDF(clienteLista);//inicializa
        exporterPDF.exportar(response);
    }

    @GetMapping("/exportarEXCEL")
    public void exportarListaClientesExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormat.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Clientes_" + fechaActual + ".xlsx"; //formato de excel

        response.setHeader(cabecera,valor);

        List<Cliente> clienteLista = clienteService.list(); //traemos la lista de clientes

        ClienteExporteEXCEL exporterExcel = new ClienteExporteEXCEL(clienteLista);//inicializa
        exporterExcel.exportar(response);
    }

}
