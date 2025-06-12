package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.model.ImagenAutoColor;
import com.automundo.concesionaria.repositorio.ImagenAutoColorRepositorio;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ImagenAutoColorServicio {

    @Autowired
    private ImagenAutoColorRepositorio imagenRepository;

    public List<ImagenAutoColor> getAll() {
        return imagenRepository.findAll();
    }

    public ImagenAutoColor save(ImagenAutoColor imagen) {
        return imagenRepository.save(imagen);
    }

    public Optional<ImagenAutoColor> getById(Long id) {
        return imagenRepository.findById(id);
    }

    public void delete(Long id) {
        imagenRepository.deleteById(id);
    }
}