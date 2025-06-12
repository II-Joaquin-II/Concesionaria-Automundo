
package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.Accesorio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccesorioRepositorio extends JpaRepository<Accesorio, Long> {
    List<Accesorio> findByNombreContainingIgnoreCase(String nombre);
}