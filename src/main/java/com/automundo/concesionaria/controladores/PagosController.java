package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Carrito;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.servicios.PedidoServicio;
import com.automundo.concesionaria.servicios.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//TARJETA
@Controller
public class PagosController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoServicio pedidoService;

    @GetMapping("/pagos")
    public String mostrarFormularioCompra(Model model, @AuthenticationPrincipal UserDetails userDetails, HttpSession session) {
        //Obtengo los datos del usuario por authentication, usa Base de datos
        String email = userDetails.getUsername();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        model.addAttribute("usuario", usuario);
        session.setAttribute("enProcesoPago", true);
        //Obtengo los datos del carrito por session, no usa Base de datos
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito != null) {
            model.addAttribute("carrito", carrito.getItems());
            model.addAttribute("total", carrito.getTotal());
        } else {
            model.addAttribute("carrito", null);
            model.addAttribute("total", 0.0);
        }

        return "Pagos";
    }
    

/*@PostMapping("/confirmacion")
public String mostrarResumenFinal(HttpSession session, Model model) {
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    if (carrito != null) {
            model.addAttribute("carrito", carrito.getItems());
            model.addAttribute("total", carrito.getTotal());

    } else {
        model.addAttribute("carrito", null);
        model.addAttribute("total", 0.0);
    }
    session.setAttribute("enProcesoPago", true);
    return "ResumenPedidos";
}*/
    
   @PostMapping("/confirmacion")
public String mostrarResumenFinal(HttpSession session, Model model) {
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    if (carrito != null) {
        // Clonas los ítems antes de vaciar
        var copiaItems = new ArrayList<>(carrito.getItems());
        double total = carrito.getTotal();

        // Vacias el carrito (afecta solo la sesión)
        carrito.vaciar();
        session.setAttribute("enProcesoPago", false);

        // Pasas la copia al modelo (no se afecta por el vaciado)
        model.addAttribute("carrito", copiaItems);
        model.addAttribute("total", total);
    } else {
        model.addAttribute("carrito", null);
        model.addAttribute("total", 0.0);
    }

    return "ResumenPedidos";
}

@GetMapping("/confirmacion")
public String mostrarResumenFinalGet(HttpSession session, Model model) {
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    if (carrito != null) {
        model.addAttribute("carrito", carrito.getItems());
        model.addAttribute("total", carrito.getTotal());
    } else {
        model.addAttribute("carrito", null);
        model.addAttribute("total", 0.0);
    }
    session.setAttribute("enProcesoPago", true);
    return "ResumenPedidos";
}
}
