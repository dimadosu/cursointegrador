package com.quimba.sistemaventa.ProyectoIntegrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;


@Service
@Transactional
public class EmailService {


    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;

    public void sendListEmail(String emailTo) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //File file =pdfService.generatePlacesPfd();
            helper.setFrom(email);
            helper.setTo(emailTo);
            helper.setSubject("Listado");
            helper.setText("Estimado cliente le enviamos su reporte de venta");
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}

