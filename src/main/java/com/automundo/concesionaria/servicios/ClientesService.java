package com.automundo.concesionaria.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Clientes;
import com.automundo.concesionaria.repositorio.ClientesRepositorio;


@Service
public class ClientesService implements UserDetailsService{

    
    @Autowired
    private ClientesRepositorio repo_clientes;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String passwordEncriptada = passwordEncoder.encode(cliente.getPass_cli());
        cliente.setPass_cli(passwordEncriptada);
        return repo_clientes.save(cliente);
    }

    public void eliminar(int id_cli) {
        repo_clientes.deleteById(id_cli);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo_clientes.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Correo electronico no encontrado"));
    }

}
    
