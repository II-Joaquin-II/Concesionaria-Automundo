package com.automundo.concesionaria.controladores;

import java.util.List;
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
import com.automundo.concesionaria.servicios.UsuarioService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/clientes")

public class ClientesController {

    @Autowired
    private UsuarioService serv_cliente;

    
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> clientes = serv_cliente.listarTodos();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable int id) {
        Optional<Usuario> cliente = serv_cliente.BuscaId(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Usuario> BuscarDNI(@PathVariable String dni) {
        Optional<Usuario> cliente = serv_cliente.BuscarDNI(dni);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/celular/{celular}")
    public ResponseEntity<Usuario> buscarCelular(@PathVariable String celular) {
        Optional<Usuario> cliente = serv_cliente.buscarCelular(celular);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
    public ResponseEntity<Usuario> insertar(@RequestBody Usuario in_cliente) {
        Usuario insertado = serv_cliente.Guardar(in_cliente);
        return new ResponseEntity<>(insertado, HttpStatus.CREATED);
    }

    /* 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrCliente(@PathVariable int id) {
        try {
            serv_cliente.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    */

    @DeleteMapping("/dni/{dni}")
    public ResponseEntity<Void> borrarClientePorDNI(@PathVariable String dni) {
        try {
            serv_cliente.eliminarPorDni(dni);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
