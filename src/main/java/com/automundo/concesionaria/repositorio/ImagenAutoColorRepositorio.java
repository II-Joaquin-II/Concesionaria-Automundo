package com.automundo.concesionaria.repositorio;


import com.automundo.concesionaria.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import com.automundo.concesionaria.model.ImagenAutoColor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//para que la tabla imagenAutoColor pueda encontrar los color del auto por su id
public interface ImagenAutoColorRepositorio extends JpaRepository<ImagenAutoColor, Long> {
@Query("SELECT DISTINCT i.color FROM ImagenAutoColor i WHERE i.auto.idAuto = :idAuto")
List<Color> findColoresByAutoId(@Param("idAuto") Long idAuto);
}
