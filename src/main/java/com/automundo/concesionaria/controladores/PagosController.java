package com.automundo.concesionaria.controladores;


import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.model.PedidoItem;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.AccesorioRepositorio;
import com.automundo.concesionaria.repositorio.AutosRepositorio;
import com.automundo.concesionaria.repositorio.PedidoRepositorio;
import com.automundo.concesionaria.servicios.PedidoServicio;
import com.automundo.concesionaria.servicios.UsuarioService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagosController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoServicio pedidoService;

    @GetMapping("/pagos")
    public String mostrarPagos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        model.addAttribute("usuario", usuario);
        return "Pagos";
    }

    @PostMapping("/pagos")
    public String procesarPago(@ModelAttribute Pedido pedido, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);

        pedido.setUsuario(usuario);

        if (pedido.getItems() != null) {
            pedido.getItems().forEach(item -> item.setPedido(pedido));
        }

        pedidoService.guardarPedido(pedido);
        return "redirect:/ResumenPedidos";
    }

    
}
