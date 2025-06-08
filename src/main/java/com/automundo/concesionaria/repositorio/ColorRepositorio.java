package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepositorio extends JpaRepository<Color, Long> {
}