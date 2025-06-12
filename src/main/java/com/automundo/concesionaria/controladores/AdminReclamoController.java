package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Reclamo;
import com.automundo.concesionaria.repositorio.ReclamoRepositorio;
import com.automundo.concesionaria.util.ListarReclamosExcel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adminverreclamos")
public class AdminReclamoController {

    private final ListarReclamosExcel listarReclamosExcel;

    @Autowired
    private ReclamoRepositorio reclamoRepositorio;

    public AdminReclamoController(ListarReclamosExcel listarReclamosExcel) {
        this.listarReclamosExcel = listarReclamosExcel;
    }
    
//Link donde se descarga el archivo excel
    @GetMapping("/descargar")
    public ModelAndView descargarExcel() {
        List<Reclamo> reclamos = reclamoRepositorio.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("reclamos", reclamos);
        return new ModelAndView(listarReclamosExcel, model);
    }
}
