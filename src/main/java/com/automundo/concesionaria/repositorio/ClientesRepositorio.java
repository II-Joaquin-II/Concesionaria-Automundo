package com.automundo.concesionaria.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automundo.concesionaria.model.Clientes;

@Repository
public interface ClientesRepositorio extends JpaRepository<Clientes, Integer>{
    Optional<Clientes> findByEmail(String email);

}
