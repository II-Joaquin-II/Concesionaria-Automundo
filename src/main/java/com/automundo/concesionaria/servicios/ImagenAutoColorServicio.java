package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.dto.ImagenAutoColorDTO;
import com.automundo.concesionaria.model.Color;
import com.automundo.concesionaria.model.ImagenAutoColor;
import com.automundo.concesionaria.repositorio.ImagenAutoColorRepositorio;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ImagenAutoColorServicio {

    @Autowired
    private ImagenAutoColorRepositorio imagenRepository;

    /*public List<ImagenAutoColor> getAll() {
        return imagenRepository.findAll();
    }
    */

    public ImagenAutoColor save(ImagenAutoColor imagen) {
        return imagenRepository.save(imagen);
    }

    public Optional<ImagenAutoColor> getById(Long id) {
        return imagenRepository.findById(id);
    }

    public void delete(Long id) {
        imagenRepository.deleteById(id);
    }
    
    //servicios para usar con DTO
     public List<Color> obtenerColoresPorAutoId(Long idAuto) {
        return imagenRepository.findColoresByAutoId(idAuto);
    }
     
       public List<ImagenAutoColorDTO> listarTodas() {
        List<ImagenAutoColor> imagenes = imagenRepository.findAll();

        return imagenes.stream().map(img -> {
            ImagenAutoColorDTO dto = new ImagenAutoColorDTO();
            dto.setIdImagen(img.getIdImagen());
            dto.setNombreArchivo(img.getNombreArchivo());
            dto.setIdColor(img.getColor().getIdColor());
            dto.setNombreColor(img.getColor().getNombreColor());
            return dto;
        }).collect(Collectors.toList());
    }
}