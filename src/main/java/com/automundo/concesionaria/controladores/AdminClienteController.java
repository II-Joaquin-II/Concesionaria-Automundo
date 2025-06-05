package com.automundo.concesionaria.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;
import com.automundo.concesionaria.util.ListarClientesExcel;
import com.automundo.concesionaria.util.ListarClientesPDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adminverclientes")
public class AdminClienteController {

    private final ListarClientesExcel listarClientesExcel;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public AdminClienteController(ListarClientesExcel listarClientesExcel) {
        this.listarClientesExcel = listarClientesExcel;
    }

    @Autowired
    private ListarClientesPDF listarClientesPDF;

    @GetMapping("/descargar-excel")
    public ModelAndView descargarExcel() {
        List<Usuario> clientes = usuarioRepositorio.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("clientes", clientes);
        return new ModelAndView(listarClientesExcel, model);
    }

    @GetMapping("/descargar-pdf")
    public void descargarReportePDF(HttpServletResponse response) throws Exception {
        List<Usuario> clientes = usuarioRepositorio.findAll()
                .stream()
                .filter(u -> u.getRoles().stream()
                .noneMatch(rol -> rol.getAuthority().equalsIgnoreCase("ROLE_ADMIN")))
                .toList();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=listado-clientes.pdf");

        listarClientesPDF.exportarPDF(clientes, response.getOutputStream());
    }

}
