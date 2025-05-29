package com.automundo.concesionaria.controladores;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.automundo.concesionaria.model.Usuario;

@ControllerAdvice
public class WebController {

    @ModelAttribute
    public void addUserNameToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Usuario cliente = (Usuario) auth.getPrincipal();
            model.addAttribute("nombreUsuario", cliente.getUsuario());
        }
    }

}
