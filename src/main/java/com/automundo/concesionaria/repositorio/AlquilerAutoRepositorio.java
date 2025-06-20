
package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.AlquilerAuto;
import com.automundo.concesionaria.model.Autos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerAutoRepositorio extends JpaRepository<AlquilerAuto, Long> {
    Optional<AlquilerAuto> findByAuto_IdAuto(Long idAuto);
}