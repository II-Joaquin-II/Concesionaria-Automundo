package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamoRepositorio extends JpaRepository<Reclamo, Integer>{
}
