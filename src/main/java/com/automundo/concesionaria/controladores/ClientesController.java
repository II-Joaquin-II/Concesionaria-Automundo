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
import com.automundo.concesionaria.model.Clientes;
import com.automundo.concesionaria.servicios.ClientesService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/clientes")

public class ClientesController {

    @Autowired
    private ClientesService serv_cliente;

    
    @GetMapping
    public ResponseEntity<List<Clientes>> listarTodos() {
        List<Clientes> clientes = serv_cliente.listarTodos();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clientes> buscar(@PathVariable int id) {
        Optional<Clientes> cliente = serv_cliente.BuscaId(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clientes> actulizaCliente(@PathVariable int id, @RequestBody Clientes cliente) {
        Optional<Clientes> clienteExiste = serv_cliente.BuscaId(id);

        if (clienteExiste.isPresent()) {
            Clientes actualizado = clienteExiste.get();
            actualizado.setNombre_cli(cliente.getNombre_cli());
            actualizado.setApellidos_cli(cliente.getApellidos_cli());
            actualizado.setDni(cliente.getDni());
            actualizado.setFecha_nac(cliente.getFecha_nac());
            actualizado.setCelular(cliente.getCelular());
            actualizado.setEmail(cliente.getEmail());
            actualizado.setUsuario_cli(cliente.getUsuario_cli());
            actualizado.setPass_cli(cliente.getPass_cli());
            return new ResponseEntity<>(serv_cliente.Guardar(actualizado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Clientes> insertar(@RequestBody Clientes in_cliente) {
        Clientes insertado = serv_cliente.Guardar(in_cliente);
        return new ResponseEntity<>(insertado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrCliente(@PathVariable int id) {
        try {
            serv_cliente.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
