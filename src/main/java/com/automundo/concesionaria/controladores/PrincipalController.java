package com.automundo.concesionaria.controladores;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {
    @GetMapping("/principal") //Link de la pagina
    public String Login(Model modelo){
        return "principal"; //Nombre del HTML que se dirige
    }
}
