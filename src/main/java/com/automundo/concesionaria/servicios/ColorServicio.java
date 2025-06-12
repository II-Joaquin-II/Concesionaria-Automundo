package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.dto.ColorDTO;
import com.automundo.concesionaria.model.Color;
import com.automundo.concesionaria.repositorio.ColorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColorServicio {

    @Autowired
    private ColorRepositorio colorRepository;

    public List<Color> getAll() {
        return colorRepository.findAll();
    }
    
    
     public List<ColorDTO> obtenerTodosLosColores() {
        List<Color> colores = colorRepository.findAll();
        return colores.stream()
                .map(color -> {
                    ColorDTO colorDTO = new ColorDTO();
                    colorDTO.setIdColor(color.getIdColor());
                    colorDTO.setNombreColor(color.getNombreColor());
                    return colorDTO;
                })
                .collect(Collectors.toList());
    }
    
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    public Optional<Color> getById(Long id) {
        return colorRepository.findById(id);
    }

    public void delete(Long id) {
        colorRepository.deleteById(id);
    }
}