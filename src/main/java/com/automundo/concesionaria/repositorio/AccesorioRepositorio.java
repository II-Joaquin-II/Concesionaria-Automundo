package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.Accesorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccesorioRepositorio extends JpaRepository<Accesorio, Long> {

    @Query("SELECT a FROM Accesorio a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) " +
           "OR LOWER(a.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Accesorio> buscarPorTexto(String texto);
}