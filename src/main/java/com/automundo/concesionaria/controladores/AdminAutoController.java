package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.repositorio.AutosRepositorio;
import com.automundo.concesionaria.util.ListarAutosExcel;
import com.automundo.concesionaria.util.ListarAutosPDF;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/adminverautos")
public class AdminAutoController {

    private final ListarAutosExcel listarAutosExcel;

    @Autowired
    private AutosRepositorio autoRepositorio;
    @Autowired
    private ListarAutosPDF listarAutosPDF;


    public AdminAutoController(ListarAutosExcel listarAutosExcel) {
        this.listarAutosExcel = listarAutosExcel;
    }

    @GetMapping("/descargar-excel")
    public ModelAndView descargarExcel() {
        List<Autos> autos = autoRepositorio.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("autos", autos);
        return new ModelAndView(listarAutosExcel, model);
    }

    @GetMapping("/descargar-pdf")
    public void descargarReportePDF(HttpServletResponse response) throws Exception {
    List<Autos> autos = autoRepositorio.findAll();

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=listado-autos.pdf");

    listarAutosPDF.exportarPDF(autos, response.getOutputStream());
    }
}