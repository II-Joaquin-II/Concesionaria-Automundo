package com.automundo.concesionaria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automundo.concesionaria.dto.EmailDTO;
import com.automundo.concesionaria.servicios.EmailServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/email")

public class EmailController {

    @Autowired
    private EmailServicio serv_email;

    @PostMapping("/enviar")
    public String postMethodName(@RequestBody EmailDTO emailDTO) {
        serv_email.enviarEmail(emailDTO.getPara(), emailDTO.getAsunto(), emailDTO.getMensaje());
        return "Correo enviado correctamente";
    }
    
}
