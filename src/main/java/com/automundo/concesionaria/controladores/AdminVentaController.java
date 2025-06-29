package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.model.PedidoItem;
import com.automundo.concesionaria.repositorio.PedidoRepositorio;
import com.automundo.concesionaria.servicios.PedidoItemServicio;
import com.automundo.concesionaria.servicios.PedidoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminVentaController {

    @Autowired
    private PedidoServicio pedidoServicio;

    @Autowired
    private PedidoItemServicio pedidoitemservicio;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @GetMapping("/adminventaauto")
    public String listarVentas(@RequestParam(name = "id", required = false) Long id,
            Model model) {

        List<Pedido> ventas = (id != null)
                ? pedidoServicio.getByIdPedido(id)
                : pedidoServicio.get();

        List<PedidoItem> ventas2 = (id != null)
                ? pedidoitemservicio.getByIdPedido(id)
                : pedidoitemservicio.get();

        model.addAttribute("ventas", ventas);
        model.addAttribute("ventas2", ventas2);
        return "adminventaauto";
    }

    /*@GetMapping("/adminventaauto")
    public String listarProductos(Model model) {
        List<Pedido> ventas = pedidoServicio.get();
        List<PedidoItem> ventas2 = pedidoitemservicio.get();
        model.addAttribute("ventas", ventas);
        model.addAttribute("ventas2", ventas2);
        return "adminventaauto";
    }*/
    @PostMapping("/adminventaauto/actualizar")
    public String actualizarEstadoVenta(@RequestParam("idPedido") Long idPedido,
            @RequestParam("estado") String estado) {
        Pedido pedido = pedidoRepositorio.findById(idPedido).orElse(null);
        if (pedido != null) {
            pedido.setEstado(estado);
            pedidoRepositorio.save(pedido);
        }
        return "redirect:/adminventaauto";
    }

}
