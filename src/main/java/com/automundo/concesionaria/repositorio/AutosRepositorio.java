
package com.automundo.concesionaria.repositorio;
import com.automundo.concesionaria.model.Autos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AutosRepositorio extends JpaRepository<Autos, Long> {

}