package com.automundo.concesionaria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class VistaController {

    @GetMapping("/admin")
    public String cargarVistaAdmin() {
        return "admin";
    }
    
    @GetMapping("/adminverclientes")
    public String cargarVistaVerClientes() {
        return "adminverclientes";
    }
    
    @GetMapping("/adminverautos")
    public String cargarVistaVerAutos() {
        return "adminverautos";
    }
    
     @GetMapping("/adminverreclamos")
    public String cargarVistaVerReclamos() {
        return "adminverreclamos";
    }
     @GetMapping("/adminventaauto")
    public String cargarVistaVentaAuto() {
        return "adminventaauto";
    }
     @GetMapping("/adminventaautoalquilado")
    public String cargarVistaVentaAutoAlquilado() {
        return "adminventaautoalquilado";
    }

}
