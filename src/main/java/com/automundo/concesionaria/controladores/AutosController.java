
package com.automundo.concesionaria.controladores;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.servicios.AutosServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/autos")

public class AutosController {
    private final AutosServicio autosServ;

    public AutosController(AutosServicio autosservicio) {
        this.autosServ = autosservicio;
    }

    @GetMapping
    public List<Autos> listarAutos() {
        return autosServ.listarAutos();
    }

    @PostMapping
    public ResponseEntity<String> insertarAuto(@RequestBody Autos auto) {
        autosServ.insertarAuto(auto);
        return ResponseEntity.ok("Auto insertado correctamente");
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Autos> buscarAutoPorId(@PathVariable Integer id) {
        return autosServ.buscarAutoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarAuto(@PathVariable Integer id, @RequestBody Autos auto) {
        auto.setIdAuto(id);
        autosServ.insertarAuto(auto); // usa el mismo save() que para insertar
        return ResponseEntity.ok("Auto actualizado correctamente");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAuto(@PathVariable Integer id) {
        autosServ.eliminarAuto(id);
        return ResponseEntity.ok("Auto eliminado correctamente");
    }

}
