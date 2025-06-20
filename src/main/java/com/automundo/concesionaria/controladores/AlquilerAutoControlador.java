package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.dto.AlquilerAutoDTO;
import com.automundo.concesionaria.servicios.AlquilerAutoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/alquiler")
@RequiredArgsConstructor
public class AlquilerAutoControlador {

    private final AlquilerAutoServicio alquilerAutoServicio;

    // GET /api/alquiler/{idAuto} -> obtiene disponibilidad del auto
    @GetMapping("/{idAuto}")
    public ResponseEntity<AlquilerAutoDTO> obtenerDisponibilidad(@PathVariable Long idAuto) {
        Optional<AlquilerAutoDTO> alquilerDTO = alquilerAutoServicio.obtenerDisponibilidadPorAutoId(idAuto);
        return alquilerDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}