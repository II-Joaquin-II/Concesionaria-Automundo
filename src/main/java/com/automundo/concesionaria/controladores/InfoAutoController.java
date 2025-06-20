package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.dto.AutoDTO;

import com.automundo.concesionaria.servicios.AutosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InfoAutoController {

    @Autowired
    private AutosServicio autoService;

    @GetMapping("/infoauto")
    public String mostrarInfoAuto(@RequestParam("idAuto") Long idAuto, Model model) {
        AutoDTO  auto = autoService.buscarAutoPorId(idAuto);
        model.addAttribute("auto", auto);
        return "infoauto";  
    }
}