package com.automundo.concesionaria.controladores;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login") 
    public String Login(Model modelo){
        return "login"; 
    }

    
}
