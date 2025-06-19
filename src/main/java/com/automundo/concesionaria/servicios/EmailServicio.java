package com.automundo.concesionaria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String para, String asunto, String mensaje) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setTo(para);
        correo.setSubject(asunto);
        correo.setText(mensaje);
        correo.setFrom("automundo.servicios.peru@gmail.com");

        mailSender.send(correo);
    }
    

}
