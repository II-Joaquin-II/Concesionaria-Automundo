package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VentaController {
    
    @GetMapping("/venta")
    public String mostrarPagos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        /*String email = userDetails.getUsername();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        model.addAttribute("usuario", usuario);*/
        return "ResumenPedidos";
    }
}
