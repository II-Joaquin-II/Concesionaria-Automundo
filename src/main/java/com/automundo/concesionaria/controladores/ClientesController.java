package com.automundo.concesionaria.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.automundo.concesionaria.model.Clientes;
import com.automundo.concesionaria.servicios.ClientesService;

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

}
