
package com.automundo.concesionaria.servicios;
import com.automundo.concesionaria.dto.AutoDTO;
import com.automundo.concesionaria.dto.ColorDTO;
import com.automundo.concesionaria.dto.ImagenAutoColorDTO;
import java.util.List;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.model.Color;
import com.automundo.concesionaria.model.ImagenAutoColor;

import com.automundo.concesionaria.repositorio.AutosRepositorio;
import com.automundo.concesionaria.repositorio.ColorRepositorio;
import com.automundo.concesionaria.repositorio.ImagenAutoColorRepositorio;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

@Service
public class AutosServicio {

    @Autowired
    private AutosRepositorio autoRepository;
     @Autowired
    private ColorRepositorio colorRepository;

    @Autowired
    private ImagenAutoColorRepositorio imagenAutoColorRepository;

    public List<AutoDTO> listarAutos() {
        List<Autos> autos = autoRepository.findAll();

        return autos.stream().map(auto -> {
            AutoDTO dto = new AutoDTO();
            dto.setIdAuto(auto.getIdAuto());
            dto.setModelo(auto.getModelo());
            dto.setMarca(auto.getMarca());
            dto.setAno(auto.getAno());
            dto.setPrecio(auto.getPrecio());
            dto.setKilometraje(auto.getKilometraje());
            dto.setTransmision(auto.getTransmision());
            dto.setCombustible(auto.getCombustible());
            dto.setEquipamiento1(auto.getEquipamiento1());
            dto.setEquipamiento2(auto.getEquipamiento2());
            dto.setEquipamiento3(auto.getEquipamiento3());
            dto.setEquipamiento4(auto.getEquipamiento4());
            dto.setCategoria(auto.getCategoria());
            dto.setEstado(auto.getEstado());

            // Mapear colores
            List<ColorDTO> colorDTOs = auto.getColores().stream().map(c -> {
                ColorDTO cdto = new ColorDTO();
                cdto.setIdColor(c.getIdColor());
                cdto.setNombreColor(c.getNombreColor());
                return cdto;
            }).collect(Collectors.toList());

            // Mapear imágenes
            List<ImagenAutoColorDTO> imagenDTOs = auto.getImagenes().stream().map(img -> {
                ImagenAutoColorDTO idto = new ImagenAutoColorDTO();
                idto.setIdImagen(img.getIdImagen());
                idto.setNombreArchivo(img.getNombreArchivo());
                idto.setIdColor(img.getColor().getIdColor());
                idto.setNombreColor(img.getColor().getNombreColor());
                return idto;
            }).collect(Collectors.toList());

            dto.setColores(colorDTOs);
            dto.setImagenes(imagenDTOs);
            return dto;

        }).collect(Collectors.toList());
    }

    public AutoDTO buscarAutoPorId(Long idAuto) {
        Autos auto = autoRepository.findById(idAuto).orElseThrow(() -> new RuntimeException("Auto no encontrado"));
        AutoDTO dto = new AutoDTO();
        dto.setIdAuto(auto.getIdAuto());
        dto.setModelo(auto.getModelo());
        dto.setMarca(auto.getMarca());
        dto.setAno(auto.getAno());
        dto.setPrecio(auto.getPrecio());
        dto.setKilometraje(auto.getKilometraje());
        dto.setTransmision(auto.getTransmision());
        dto.setCombustible(auto.getCombustible());
        dto.setEquipamiento1(auto.getEquipamiento1());
        dto.setEquipamiento2(auto.getEquipamiento2());
        dto.setEquipamiento3(auto.getEquipamiento3());
        dto.setEquipamiento4(auto.getEquipamiento4());
        dto.setCategoria(auto.getCategoria());
        dto.setEstado(auto.getEstado());

        // Mapear colores
        List<ColorDTO> colorDTOs = auto.getColores().stream().map(c -> {
            ColorDTO cdto = new ColorDTO();
            cdto.setIdColor(c.getIdColor());
            cdto.setNombreColor(c.getNombreColor());
            return cdto;
        }).collect(Collectors.toList());

        // Mapear imágenes
        List<ImagenAutoColorDTO> imagenDTOs = auto.getImagenes().stream().map(img -> {
            ImagenAutoColorDTO idto = new ImagenAutoColorDTO();
            idto.setIdImagen(img.getIdImagen());
            idto.setNombreArchivo(img.getNombreArchivo());
            idto.setIdColor(img.getColor().getIdColor());
            idto.setNombreColor(img.getColor().getNombreColor());
            return idto;
        }).collect(Collectors.toList());

        dto.setColores(colorDTOs);
        dto.setImagenes(imagenDTOs);
        return dto;
    }
    
     public AutoDTO insertarAuto(AutoDTO autoDTO) {
    Autos auto = new Autos();
    auto.setModelo(autoDTO.getModelo());
    auto.setMarca(autoDTO.getMarca());
    auto.setAno(autoDTO.getAno());
    auto.setPrecio(autoDTO.getPrecio());
    auto.setKilometraje(autoDTO.getKilometraje());
    auto.setTransmision(autoDTO.getTransmision());
    auto.setCombustible(autoDTO.getCombustible());
    auto.setEquipamiento1(autoDTO.getEquipamiento1());
    auto.setEquipamiento2(autoDTO.getEquipamiento2());
    auto.setEquipamiento3(autoDTO.getEquipamiento3());
    auto.setEquipamiento4(autoDTO.getEquipamiento4());
    auto.setCategoria(autoDTO.getCategoria());
    auto.setEstado(autoDTO.getEstado());

    List<Color> colores = new ArrayList<>();
    if (autoDTO.getColores() != null) {
        for (ColorDTO colorDTO : autoDTO.getColores()) {
             Color color = colorRepository.findById(colorDTO.getIdColor()).orElse(null);
            Preconditions.checkNotNull(color, "Color no encontrado");
            colores.add(color);
        }
        auto.setColores(colores);
    }

    Autos autoGuardado = autoRepository.save(auto);

    if (autoDTO.getImagenes() != null) {
        for (ImagenAutoColorDTO imgDTO : autoDTO.getImagenes()) {
            Color color = colorRepository.findById(imgDTO.getIdColor()).orElse(null);
            Preconditions.checkNotNull(color, "Color no encontrado para imagen");
            ImagenAutoColor imagen = new ImagenAutoColor();
            imagen.setAuto(autoGuardado);
            imagen.setColor(color);
            imagen.setNombreArchivo(imgDTO.getNombreArchivo());
            imagenAutoColorRepository.save(imagen);
        }
    }

    return autoDTO;
}
     
     
       public AutoDTO editarAuto(Long idAuto, AutoDTO autoDTO) {
    Autos autoExistente = autoRepository.findById(idAuto)
            .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

    // Actualizar los datos básicos del auto
    autoExistente.setModelo(autoDTO.getModelo());
    autoExistente.setMarca(autoDTO.getMarca());
    autoExistente.setAno(autoDTO.getAno());
    autoExistente.setPrecio(autoDTO.getPrecio());
    autoExistente.setKilometraje(autoDTO.getKilometraje());
    autoExistente.setTransmision(autoDTO.getTransmision());
    autoExistente.setCombustible(autoDTO.getCombustible());
    autoExistente.setEquipamiento1(autoDTO.getEquipamiento1());
    autoExistente.setEquipamiento2(autoDTO.getEquipamiento2());
    autoExistente.setEquipamiento3(autoDTO.getEquipamiento3());
    autoExistente.setEquipamiento4(autoDTO.getEquipamiento4());
    autoExistente.setCategoria(autoDTO.getCategoria());
    autoExistente.setEstado(autoDTO.getEstado());

    // Actualizar los colores del auto
    autoExistente.getColores().clear(); // Limpiar los colores existentes
    for (ColorDTO colorDTO : autoDTO.getColores()) {
        Color colorExistente = colorRepository.findById(colorDTO.getIdColor())
                .orElseThrow(() -> new RuntimeException("Color no encontrado"));
        autoExistente.getColores().add(colorExistente);
    }

    // Actualizar las imágenes del auto
    autoExistente.getImagenes().clear(); // Limpiar las imágenes existentes
    for (ImagenAutoColorDTO imagenDTO : autoDTO.getImagenes()) {
        Color color = colorRepository.findById(imagenDTO.getIdColor())
                .orElseThrow(() -> new RuntimeException("Color no encontrado"));

        ImagenAutoColor imagenAutoColor = new ImagenAutoColor();
        imagenAutoColor.setAuto(autoExistente);
        imagenAutoColor.setColor(color);
        imagenAutoColor.setNombreArchivo(imagenDTO.getNombreArchivo());

        imagenAutoColorRepository.save(imagenAutoColor); // Guardar la nueva imagen

        // Agregar la imagen a la lista de imágenes del auto
        autoExistente.getImagenes().add(imagenAutoColor);
    }

    // Guardar el auto actualizado
    autoRepository.save(autoExistente);

    // Devolver el AutoDTO con los datos actualizados (sin conversión intermedia)
    autoDTO.setIdAuto(autoExistente.getIdAuto());  // Establecer el ID del auto
    return autoDTO;
}
     
        public void eliminarAuto(Long idAuto) {
        Autos auto = autoRepository.findById(idAuto).orElseThrow(() -> new RuntimeException("Auto no encontrado"));
        autoRepository.delete(auto);
    }
        
        
        
    
    public Autos save(Autos auto) {
        return autoRepository.save(auto);
    }

    public Optional<Autos> getById(Long id) {
        return autoRepository.findById(id);
    }

    public void delete(Long id) {
        autoRepository.deleteById(id);
    }
    
   public List<AutoDTO> getAllAutosConDetalle() {
        List<Autos> autos = autoRepository.findAll();

        return autos.stream().map(auto -> {
            AutoDTO dto = new AutoDTO();
            dto.setIdAuto(auto.getIdAuto());
            dto.setModelo(auto.getModelo());
            dto.setMarca(auto.getMarca());
            dto.setAno(auto.getAno());
            dto.setPrecio(auto.getPrecio());
            dto.setKilometraje(auto.getKilometraje());
            dto.setTransmision(auto.getTransmision());
            dto.setCombustible(auto.getCombustible());
            dto.setEquipamiento1(auto.getEquipamiento1());
            dto.setEquipamiento2(auto.getEquipamiento2());
            dto.setEquipamiento3(auto.getEquipamiento3());
            dto.setEquipamiento4(auto.getEquipamiento4());
            dto.setCategoria(auto.getCategoria());
            dto.setEstado(auto.getEstado());

            // Mapear colores
            List<ColorDTO> colorDTOs = auto.getColores().stream().map(c -> {
                ColorDTO cdto = new ColorDTO();
                cdto.setIdColor(c.getIdColor());
                cdto.setNombreColor(c.getNombreColor());
                return cdto;
            }).collect(Collectors.toList());

            // Mapear imágenes
            List<ImagenAutoColorDTO> imagenDTOs = auto.getImagenes().stream().map(img -> {
                ImagenAutoColorDTO idto = new ImagenAutoColorDTO();
                idto.setIdImagen(img.getIdImagen());
                idto.setNombreArchivo(img.getNombreArchivo());
                idto.setIdColor(img.getColor().getIdColor());
                idto.setNombreColor(img.getColor().getNombreColor());
                return idto;
            }).collect(Collectors.toList());

            dto.setColores(colorDTOs);
            dto.setImagenes(imagenDTOs);
            return dto;

        }).collect(Collectors.toList());
    }
    
    
    
}