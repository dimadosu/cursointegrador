package com.quimba.sistemaventa.ProyectoIntegrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import java.io.File;

import javax.mail.BodyPart;
import javax.mail.internet.MimeMessage;


@Service
@Transactional
public class EmailService {


    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;



    public void sendListEmail(String emailTo, String mensaje, String archivo) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            //File file =pdfService.generatePlacesPfd();
            helper.setFrom(email); //remitente
            helper.setTo(emailTo); //destinatario
            helper.setSubject("Comprovante de compra"); //titulo
            helper.addAttachment(archivo,new File("C:\\Users\\dijes\\Documents\\REPORTESQUIMBA\\"+ archivo));
            //envio del archivo que completa la ruta de la carpeta
            helper.setText(mensaje); //cuerpo del mensaje
            javaMailSender.send(message); //se lo asignamos
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}

