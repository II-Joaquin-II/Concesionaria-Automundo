package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Carrito;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.AccesorioRepositorio;
import com.automundo.concesionaria.repositorio.AutosRepositorio;
import com.automundo.concesionaria.repositorio.PedidoRepositorio;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;
import com.automundo.concesionaria.servicios.PedidoServicio;
import com.automundo.concesionaria.servicios.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
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
    private PedidoRepositorio pedidoRepo;

    @Autowired
    private AutosRepositorio autosRepo;
    @Autowired
    private AccesorioRepositorio accesorioRepo;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

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

    @PostMapping("/confirmacion")
    public String mostrarResumenFinal(HttpSession session, Model model) {
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito != null) {
            // Clonamos los ítems antes de vaciar
            var copiaItems = new ArrayList<>(carrito.getItems());
            double total = carrito.getTotal();

            // Vaciamos el carrito
            carrito.vaciar();
            session.setAttribute("enProcesoPago", false);

            // Pasamos la copia al modelo
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
