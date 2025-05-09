package com.automundo.concesionaria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class VistaController {

    @GetMapping("/admin")
    public String cargarVistaAdmin() {
        return "admin";
    }

}
