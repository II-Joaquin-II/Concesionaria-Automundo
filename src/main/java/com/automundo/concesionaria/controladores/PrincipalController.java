package com.automundo.concesionaria.controladores;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/principal") //Link de la pagina
    public String Login(Model modelo){
        return "principal"; //Nombre del HTML que se dirige
    }

    @GetMapping("/index")
    public String Index(Model modelo){
        return "index"; 
    }
    
    @GetMapping("/reclamos")
    public String mostrarFormularioReclamos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // recupero los datos del usuario
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        model.addAttribute("usuario", usuario);
        return "reclamos";
    }
}
