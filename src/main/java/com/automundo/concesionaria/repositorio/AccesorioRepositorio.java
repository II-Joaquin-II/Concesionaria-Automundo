
package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AccesorioRepositorio extends JpaRepository<Accesorio, Long> {
    List<Accesorio> findByNombreContainingIgnoreCase(String nombre);
}