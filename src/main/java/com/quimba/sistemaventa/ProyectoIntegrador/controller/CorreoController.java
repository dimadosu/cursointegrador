package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/correo")
public class CorreoController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviarCorreo")
    public ModelAndView sendPdfEmail(@RequestParam("email") String email, @RequestParam("mensaje") String mensaje, @RequestParam("archivo") String archivo){
        this.emailService.sendListEmail(email, mensaje, archivo);
        return new ModelAndView("/correo/mensaje");
    }

    @GetMapping("/irPagina")
    public ModelAndView irVentanaCorreo(){

        return new ModelAndView("/correo/form_correo");
    }


}
