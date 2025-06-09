
package com.automundo.concesionaria.controladores;
import com.automundo.concesionaria.dto.AutoDTO;
import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.servicios.AutosServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


import org.springframework.web.bind.annotation.RequestBody;






@RestController
@RequestMapping("/api/autos")
public class AutosController {

    @Autowired
    private AutosServicio autoService;

     @GetMapping
    public ResponseEntity<List<AutoDTO>> listarAutos() {
        List<AutoDTO> autos = autoService.listarAutos();
        return ResponseEntity.ok(autos);
    }

    @PostMapping
    public ResponseEntity<AutoDTO> insertarAuto(@RequestBody AutoDTO autoDTO) {
        AutoDTO autoCreado = autoService.insertarAuto(autoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(autoCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoDTO> buscarAuto(@PathVariable Long id) {
        AutoDTO auto = autoService.buscarAutoPorId(id);
        return ResponseEntity.ok(auto);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<AutoDTO> editarAuto(@PathVariable Long id, @RequestBody AutoDTO autoDTO) {
        AutoDTO autoActualizado = autoService.editarAuto(id, autoDTO);
        return ResponseEntity.ok(autoActualizado);
    }
    
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAuto(@PathVariable Long id) {
        autoService.eliminarAuto(id);
        return ResponseEntity.noContent().build();
    }
}


