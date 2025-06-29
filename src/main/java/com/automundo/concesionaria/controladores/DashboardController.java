package com.automundo.concesionaria.controladores;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.automundo.concesionaria.servicios.DashboardServicio;

@Controller
public class DashboardController {

    @Autowired
    private DashboardServicio DashboardServicio;

    @GetMapping("/admin")
    public String mostrarDashboard(Model model) {
        long pendientes = DashboardServicio.contarPorEstado("pendiente");
        long entregados = DashboardServicio.contarPorEstado("entregado");
        long enProceso = DashboardServicio.contarPorEstado("en proceso");

        model.addAttribute("pendientes", pendientes);
        model.addAttribute("entregados", entregados);
        model.addAttribute("enProceso", enProceso);

        Map<String, Long> accesoriosVendidos = DashboardServicio.obtenerAccesoriosVendidos();

        model.addAttribute("nombresAccesorios", new ArrayList<>(accesoriosVendidos.keySet()));
        model.addAttribute("cantidadesAccesorios", new ArrayList<>(accesoriosVendidos.values()));

        return "admin";
    }

}
