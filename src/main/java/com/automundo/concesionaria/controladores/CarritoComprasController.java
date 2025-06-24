package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Carrito;
import com.automundo.concesionaria.model.Item;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@RequestMapping("/carrito")
@SessionAttributes("carrito")
public class CarritoComprasController {
    
    @ModelAttribute("carrito")
    public Carrito carrito() {
        return new Carrito();
    }

    @GetMapping("/carritocompras")
    public String verCarrito(@ModelAttribute("carrito") Carrito carrito, Model model) {
        model.addAttribute("items", carrito.getItems());
        model.addAttribute("total", carrito.getTotal());
        return "CarritoCompras";
    }
    
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarAlCarrito(@RequestParam Long id,
                                              @RequestParam String nombre,
                                              @RequestParam double precio,
                                              @RequestParam String color,
                                              @ModelAttribute("carrito") Carrito carrito) {
        carrito.agregarItem(new Item(id, nombre, precio, color));
        return ResponseEntity.ok().body(Map.of(
            "success", true,
            "total", carrito.getTotal(),
            "items", carrito.getItems()
        ));
    }

    @PostMapping("/eliminar")
    public ResponseEntity<?> eliminarDelCarrito(@RequestParam Long id,
                                                @ModelAttribute("carrito") Carrito carrito) {
        carrito.eliminarItem(id);
        return ResponseEntity.ok().body(Map.of(
            "success", true,
            "total", carrito.getTotal(),
            "items", carrito.getItems()
        ));
    }
}
