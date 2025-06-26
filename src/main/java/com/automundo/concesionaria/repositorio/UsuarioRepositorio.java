package com.automundo.concesionaria.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.automundo.concesionaria.model.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT c FROM Usuario c JOIN c.roles r WHERE r.nombre = :nombreRol")
    List<Usuario> findByRolNombre(@Param("nombreRol") String nombreRol);

    Optional<Usuario> findByDni(String dni);

    Optional<Usuario> findByCelular(String celular);

    Optional<Usuario> findByUsuario(String usuario);

}
