package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.dto.ReclamoDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.automundo.concesionaria.model.Reclamo;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.ReclamoRepositorio;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;
import com.automundo.concesionaria.servicios.ReclamoServicio;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {

    @Autowired
    private ReclamoServicio reclamoserv;

    @Autowired
    private ReclamoRepositorio reclamoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/Nuevo")
    public ResponseEntity<?> crearReclamo(@RequestBody ReclamoDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        // Obtenemos el email del usuario autenticado
        String email = userDetails.getUsername();

        // Buscamos al usuario por su email
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        Reclamo reclamo = new Reclamo();
        reclamo.setFechaincidente(dto.getFechaincidente());
        reclamo.setMotivoReclamo(dto.getMotivoReclamo());
        reclamo.setTipo_Vehiculo(dto.getTipo_Vehiculo());
        reclamo.setDetalle(dto.getDetalle());
        reclamo.setEstadoReclamo("pendiente");
        reclamo.setIdCliente(usuario);
        reclamo.setFechaReclamo(new Timestamp(System.currentTimeMillis()));
        reclamoRepositorio.save(reclamo);
        return ResponseEntity.ok("Reclamo registrado con éxito");
    }

    @GetMapping
    public List<Reclamo> listarReclamo() {
        return reclamoserv.listarReclamos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> buscarReclamoPorId(@PathVariable Integer id) {
        return reclamoserv.buscarReclamoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarReclamo(@PathVariable Integer id, @RequestBody ReclamoDTO dto) {
        Optional<Reclamo> reclamoOpt = reclamoRepositorio.findById(id);
        if (reclamoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reclamo reclamo = reclamoOpt.get();

        reclamo.setEstadoReclamo(dto.getEstadoReclamo());

        reclamoRepositorio.save(reclamo);
        return ResponseEntity.ok("Reclamo actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarReclamo(@PathVariable Integer id) {
        reclamoserv.eliminarReclamo(id);
        return ResponseEntity.ok("Reclamo eliminado correctamente");
    }

}
