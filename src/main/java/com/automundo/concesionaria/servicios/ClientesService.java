package com.automundo.concesionaria.servicios;

import java.util.List;
import java.util.Optional;
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

    public Optional<Clientes> BuscaId(int id_cli) {
        return repo_clientes.findById(id_cli);
    }

    public Clientes Actualizar(int id_cli, Clientes actualiza) {
        Clientes clienteExiste = repo_clientes.findById(id_cli)
        .orElseThrow(() -> new RuntimeException("Cliente no existe" + id_cli));
        return repo_clientes.save(clienteExiste);
    }

    public Clientes Guardar(Clientes cliente) {
        return repo_clientes.save(cliente);
    }

    public void eliminar(int id_cli) {
        repo_clientes.deleteById(id_cli);
    }

}
    
