package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.model.Carrito;
import com.automundo.concesionaria.model.Item;
import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.model.PedidoItem;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.AccesorioRepositorio;
import com.automundo.concesionaria.repositorio.AutosRepositorio;
import com.automundo.concesionaria.repositorio.PedidoRepositorio;
import com.automundo.concesionaria.servicios.UsuarioService;
import com.automundo.concesionaria.util.ResumenPedidoPDF;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private UsuarioService usuarioService;

    @Autowired
    private ResumenPedidoPDF carritoPdf;
     
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
@Transactional
public String confirmarPago(HttpSession session,
                             Model model,
                             @AuthenticationPrincipal UserDetails user) {

    // 1. Recuperar selección real
    Long   idAutoReal = (Long)  session.getAttribute("idAutoSeleccionado");
    String colorAuto  = (String) session.getAttribute("colorAutoSeleccionado");
    if (idAutoReal == null) throw new IllegalStateException("No hay auto en sesión");

    // 2. Obtener el carrito (contiene item id 1000 + accesorios)
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    if (carrito == null) throw new IllegalStateException("Carrito vacío");

    // 3. Construir y guardar el Pedido
    Usuario usuario = usuarioService.obtenerUsuarioPorEmail(user.getUsername());
    Autos   auto    = autosRepo.findById(idAutoReal)
                               .orElseThrow(() -> new IllegalArgumentException("Auto no encontrado"));

    Pedido pedido = new Pedido();
    pedido.setUsuario(usuario);
    pedido.setAutos(auto);
    pedido.setColorauto(colorAuto);
    pedido.setTotal(BigDecimal.valueOf(carrito.getTotal()));
    
    // 4. Crear items SOLO para los accesorios (id ≠ 1000)
    for (Item it : carrito.getItems()) {
        if (it.getId() == 1000) continue;              // omitimos placeholder
        Accesorio acc = accesorioRepo.findById(it.getId())
                                     .orElseThrow(() ->
                                         new IllegalArgumentException(
                                             "Accesorio no encontrado, id=" + it.getId()));

        PedidoItem pi = new PedidoItem();
        pi.setPedido(pedido);
        pi.setAccesorio(acc);
        pi.setColoracc(it.getColor());
        pi.setPrecioUnitario(BigDecimal.valueOf(it.getPrecio()));
        pedido.getItems().add(pi);
    }

    pedidoRepo.save(pedido);   // <— persistimos

    /* 5. Preparamos la vista y vaciamos el carrito */
    var copia = new ArrayList<>(carrito.getItems());
    double total = carrito.getTotal();
    session.setAttribute("carritoFinal", copia);       // ⬅️ guardamos para el PDF
    session.setAttribute("totalFinal",   total); 
    carrito.vaciar();
    session.removeAttribute("idAutoSeleccionado");
    session.removeAttribute("colorAutoSeleccionado");

    model.addAttribute("carrito", copia);
    model.addAttribute("total", total);
    session.setAttribute("enProcesoPago", false);
    return "redirect:/resumen";
}

@GetMapping("/resumen")
public String mostrarResumen(HttpSession session, Model model) {

    @SuppressWarnings("unchecked")
    List<Item> items  = (List<Item>) session.getAttribute("carritoFinal");
    Double     total  = (Double)       session.getAttribute("totalFinal");

    if (items == null || items.isEmpty()) {
        // No hay datos → vuelve a la página de pago o muestra un aviso
        return "redirect:/pagos";
    }

    model.addAttribute("carrito", items);
    model.addAttribute("total",   total);
    return "ResumenPedidos";   // tu misma plantilla
}

@PostMapping("/carrito/seleccionAuto")
@ResponseBody
public ResponseEntity<String> guardarSeleccionAuto(HttpSession session,
                                                   @RequestParam Long idAuto,
                                                   @RequestParam String color) {
    session.setAttribute("idAutoSeleccionado",  idAuto);
    session.setAttribute("colorAutoSeleccionado", color);
    return ResponseEntity.ok("ok");
}

 @GetMapping("/pedido/pdf")
    public void descargarPdfPedido(HttpSession session,
                                   HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Item> items = (List<Item>) session.getAttribute("carritoFinal");
        Double     total = (Double)       session.getAttribute("totalFinal");

        if (items == null || items.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "No hay datos para generar el PDF.");
            return;
        }

        response.setContentType("application/pdf");
        String filename = "pedido_" + LocalDate.now() + ".pdf";
        response.setHeader("Content-Disposition",
                           "attachment; filename=" + filename);

        carritoPdf.exportar(items, total, response.getOutputStream());
    }
}
