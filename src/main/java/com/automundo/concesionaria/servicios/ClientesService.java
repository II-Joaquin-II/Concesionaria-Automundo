package com.automundo.concesionaria.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Clientes;
import com.automundo.concesionaria.repositorio.ClientesRepositorio;


@Service
public class ClientesService {

    
    @Autowired
    private ClientesRepositorio repo_clientes;

    public List<Clientes> listarTodos() {
        return repo_clientes.findAll();
    }


}
    
