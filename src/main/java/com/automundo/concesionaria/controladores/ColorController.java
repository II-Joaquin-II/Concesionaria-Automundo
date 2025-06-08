package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.dto.ColorDTO;
import com.automundo.concesionaria.model.Color;
import com.automundo.concesionaria.repositorio.ColorRepositorio;
import com.automundo.concesionaria.servicios.ColorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/colores")
public class ColorController {

    @Autowired
    private ColorRepositorio colorRepository;
      @Autowired
    private ColorServicio colorService;


    @GetMapping
    public List<Color> listar() {
        return colorRepository.findAll();
    }

     @GetMapping("/api/colores")
    public List<ColorDTO> obtenerColores() {
         return colorService.obtenerTodosLosColores();
    }
    
    @PostMapping
    public Color agregar(@RequestBody Color color) {
        return colorRepository.save(color);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        colorRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}