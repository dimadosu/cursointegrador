package com.quimba.sistemaventa.ProyectoIntegrador.controller;

import com.quimba.sistemaventa.ProyectoIntegrador.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/correo")
public class CorreoController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/pdf")
    public void sendPdfEmail(){
        this.emailService.sendListEmail("doloriertdiego010@gmail.com");
    }


}
