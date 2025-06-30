package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.repositorio.AccesorioRepositorio;
import com.automundo.concesionaria.servicios.AccesorioServicio;
import com.automundo.concesionaria.util.ListarAccesoriosExcel;
import com.automundo.concesionaria.util.ListarAccesoriosPDF;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AccesorioController {
    
    private final ListarAccesoriosExcel listarAccesoriosExcel;
    
    @Autowired
    private AccesorioRepositorio accesorioRepositorio;


    @Autowired
    private AccesorioServicio servicio;
     @Autowired
    private ListarAccesoriosPDF listarAccesoriosPDF;

    public AccesorioController(ListarAccesoriosExcel listarAccesoriosExcel) {
        this.listarAccesoriosExcel = listarAccesoriosExcel;
    }

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

    @GetMapping("/descargar-excel")
    public ModelAndView descargarExcel() {
        List<Accesorio> accesorios = accesorioRepositorio.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("accesorios", accesorios);
        return new ModelAndView(listarAccesoriosExcel, model);
    }

    @GetMapping("/descargar-pdf")
    public void descargarReportePDF(HttpServletResponse response) throws Exception {
        List<Accesorio> accesorios = accesorioRepositorio.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=listado-accesorios.pdf");

        listarAccesoriosPDF.exportarPDF(accesorios, response.getOutputStream());
    }

    @GetMapping("/vista-accesorios.html")
    public String vistaAccesorios(Model model) {
        List<Accesorio> accesorios = servicio.listarTodos();
        model.addAttribute("accesorios", accesorios);
        return "vista-accesorios";
    }

}
