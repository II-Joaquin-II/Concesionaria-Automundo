package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.model.Carrito;
import com.automundo.concesionaria.model.Item;
import com.automundo.concesionaria.servicios.AccesorioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AccesorioController {

    @Autowired
    private AccesorioServicio servicio;

    @GetMapping("/admin-accesorios")
    public String verPaginaAdmin(Model modelo) {
        List<Accesorio> lista = servicio.listarTodos();
        modelo.addAttribute("listadoAccesorios", lista);
        modelo.addAttribute("totalAccesorios", lista.size());
        return "admin-accesorios";
    }

    @PostMapping("/guardarAccesorio")
    public String guardar(@ModelAttribute Accesorio accesorio) {
        servicio.guardar(accesorio);
        return "redirect:/admin-accesorios";
    }

    @GetMapping("/editarAccesorio/{id}")
    @ResponseBody
    public Accesorio editar(@PathVariable("id") Long id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping("/actualizarAccesorio")
    public String actualizar(@ModelAttribute Accesorio accesorio) {
        servicio.guardar(accesorio);
        return "redirect:/admin-accesorios";
    }

    @GetMapping("/eliminarAccesorio/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        servicio.eliminar(id);
        return "redirect:/admin-accesorios";
    }

    @GetMapping("/buscarAccesorio")
    public String buscar(@RequestParam("nombre") String nombre, Model modelo) {
        List<Accesorio> resultados = servicio.buscarPorNombre(nombre);
        modelo.addAttribute("listadoAccesorios", resultados);
        modelo.addAttribute("totalAccesorios", resultados.size());
        return "admin-accesorios";
    }

    @GetMapping("/vista-accesorios.html")
    public String vistaAccesorios(Model model) {
        List<Accesorio> accesorios = servicio.listarTodos();
        model.addAttribute("accesorios", accesorios);
        return "vista-accesorios"; 
    }
    
   /* @PostMapping("/agregar-auto")
    public String agregarAutoAlCarrito(@RequestParam Long id,
                                       @RequestParam String nombre,
                                       @RequestParam double precio,
                                       @RequestParam String color,
                                       @ModelAttribute("carrito") Carrito carrito) {

        carrito.agregarItem(new Item(id, nombre, precio, color)); // ← lo mismo que haces en Pagos
        return "redirect:/vista-accesorios";
    }*/
}
