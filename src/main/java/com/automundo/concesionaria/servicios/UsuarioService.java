package com.automundo.concesionaria.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;
import org.apache.commons.lang3.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import com.automundo.concesionaria.repositorio.RolRepositorio;


@Service
public class UsuarioService implements UserDetailsService{

    
    @Autowired
    private UsuarioRepositorio repo_clientes;

    @Autowired
    private RolRepositorio repo_rol;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        //return repo_clientes.findAll();
        return repo_clientes.findByRolNombre("ROLE_USER");
    }

    public Optional<Usuario> BuscaId(int id_cli) {
        //return repo_clientes.findById(id_cli);
        Optional<Usuario> cliente = repo_clientes.findById(id_cli);
        return cliente.filter(c -> c.getRoles().stream().anyMatch(r -> "ROLE_USER".equals(r.getNombre())));
    }

    public Optional<Usuario> BuscarDNI(String dni) {
        return repo_clientes.findByDni(dni)
        .filter(c -> c.getRoles().stream().anyMatch(r -> "ROLE_USER".equals(r.getNombre())));
    }

    public Optional<Usuario> buscarCelular(String celular) {
        return repo_clientes.findByCelular(celular)
        .filter(c -> c.getRoles().stream().anyMatch(r -> "ROLE_USER".equals(r.getNombre())));
    }

    public Usuario Actualizar(int id_cli, Usuario actualiza) {
        
        Usuario clienteExiste = repo_clientes.findById(id_cli)
        .orElseThrow(() -> new RuntimeException("Cliente no existe: " + id_cli));

        clienteExiste.setNombre_usuario(actualiza.getNombre_usuario());
        clienteExiste.setApellidos_usuario(actualiza.getApellidos_usuario());
        clienteExiste.setDni(actualiza.getDni());
        clienteExiste.setFecha_nac(actualiza.getFecha_nac());
        clienteExiste.setCelular(actualiza.getCelular());
        clienteExiste.setEmail(actualiza.getEmail());
        clienteExiste.setUsuario(actualiza.getUsuario());

        /* 
        if (actualiza.getPass() != null && !actualiza.getPass().isEmpty()) {

        String passwordEncriptada = passwordEncoder.encode(actualiza.getPass());
        //clienteExiste.setPass(passwordEncriptada);(passwordEncriptada);
        clienteExiste.setPass(passwordEncriptada);
        }
        */

        //implementacion de apache comments
        if (StringUtils.isNotBlank(actualiza.getPass())) {

            String passwordEncriptada = passwordEncoder.encode(actualiza.getPass());
            clienteExiste.setPass(passwordEncriptada);

        }

        return repo_clientes.save(clienteExiste);
    }

    public Usuario Guardar(Usuario cliente) {
        String passwordEncriptada = passwordEncoder.encode(cliente.getPass());
        cliente.setPass(passwordEncriptada);

        var rolUser = repo_rol.findByNombre("ROLE_USER")
        .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));

        cliente.setRoles(List.of(rolUser));

        return repo_clientes.save(cliente);
    }

    public void eliminarPorDni(String dni) {
        Optional<Usuario> cliente = repo_clientes.findByDni(dni)
        .filter(c -> c.getRoles().stream().anyMatch(r -> "ROLE_USER".equals(r.getNombre())));

        if (cliente.isPresent()) {
        repo_clientes.deleteById(cliente.get().getId_usuario());
        } else {
        throw new EntityNotFoundException("Cliente no encontrado con DNI: " + dni);
        }

    }
    
    //Recupero los datos de usuario para usarlo en las reclamaciones
    public Usuario obtenerUsuarioPorEmail(String email) {
        return repo_clientes.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo_clientes.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Correo electronico no encontrado"));
    }

}
    
