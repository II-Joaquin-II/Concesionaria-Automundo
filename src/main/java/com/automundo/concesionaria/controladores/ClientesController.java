package com.automundo.concesionaria.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.servicios.EmailServicio;
import com.automundo.concesionaria.servicios.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping("/api/clientes")

public class ClientesController {

    @Autowired
    private UsuarioService serv_cliente;

    @Autowired
    private EmailServicio serv_email;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> clientes = serv_cliente.listarTodos();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable int id) {
        Optional<Usuario> cliente = serv_cliente.BuscaId(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Map<String, Object>> BuscarDNI(@PathVariable String dni) {
        Map<String, Object> response = new HashMap<>();

        if (StringUtils.isBlank(dni)) {
            response.put("error", "DNI vacío o inválido");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Usuario> cliente = serv_cliente.BuscarDNI(dni);
        response.put("cliente", cliente.orElse(null));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/celular/{celular}")
    public ResponseEntity<Map<String, Object>> buscarCelular(@PathVariable String celular) {
        Map<String, Object> response = new HashMap<>();

        if (StringUtils.isBlank(celular)) {
            response.put("error", "Celular vacío o inválido");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Usuario> cliente = serv_cliente.buscarCelular(celular);
        response.put("cliente", cliente.orElse(null));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<Map<String, Object>> buscarPorUsuario(@PathVariable String usuario) {
        Map<String, Object> response = new HashMap<>();

        if (StringUtils.isBlank(usuario)) {
            response.put("error", "Nombre de usuario vacío o inválido");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Usuario> cliente = serv_cliente.buscarPorUsuario(usuario);
        response.put("cliente", cliente.orElse(null));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actulizaCliente(@PathVariable int id, @RequestBody Usuario cliente) {
        try {
            Usuario actualizado = serv_cliente.Actualizar(id, cliente);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody Usuario in_cliente) {
        Optional<Usuario> existentePorDni = serv_cliente.BuscarDNI(in_cliente.getDni());

        if (existentePorDni.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El DNI ya está registrado.");
        }

        Optional<Usuario> existentePorCelular = serv_cliente.buscarCelular(in_cliente.getCelular());
        if (existentePorCelular.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El número de celular ya está registrado.");
        }

        Optional<Usuario> existentePorEmail = serv_cliente.buscarPorEmail(in_cliente.getEmail());
        if (existentePorEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo electrónico ya está registrado.");
        }

        Optional<Usuario> existentePorUsuario = serv_cliente.buscarPorUsuario(in_cliente.getUsuario());
        if (existentePorUsuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El nombre de usuario ya está registrado.");
        }

        Usuario insertado = serv_cliente.Guardar(in_cliente);

        if (StringUtils.isNotBlank(insertado.getEmail())) {
            String para = insertado.getEmail();
            String asunto = "Bienvenido a AutoMundo Perú";
            String mensaje = "Hola " + insertado.getNombre_usuario() + " " + insertado.getApellidos_usuario() + ",\n\n"
                    + "¡Gracias por registrarte en AutoMundo Perú!\n"
                    + "Estamos encantados de tenerte con nosotros.\n\n"
                    + "Saludos,\nEl equipo de AutoMundo.";
            serv_email.enviarEmail(para, asunto, mensaje);
        }

        return new ResponseEntity<>(insertado, HttpStatus.CREATED);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> buscarPorEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();

        if (StringUtils.isBlank(email)) {
            response.put("error", "Email vacío o inválido");
            return ResponseEntity.badRequest().body(response);
        }

        String emailNormalizado = email.trim().toLowerCase();
        Optional<Usuario> cliente = serv_cliente.buscarPorEmail(emailNormalizado);
        response.put("cliente", cliente.orElse(null));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/dni/{dni}")
    public ResponseEntity<Void> borrarClientePorDNI(@PathVariable String dni) {
        if (StringUtils.isBlank(dni)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            serv_cliente.eliminarPorDni(dni);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
