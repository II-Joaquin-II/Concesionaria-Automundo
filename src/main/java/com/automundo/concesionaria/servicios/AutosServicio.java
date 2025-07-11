
package com.automundo.concesionaria.servicios;
import com.automundo.concesionaria.dto.AutoDTO;
import com.automundo.concesionaria.dto.ColorDTO;
import com.automundo.concesionaria.dto.ImagenAutoColorDTO;
import com.automundo.concesionaria.model.AlquilerAuto;
import java.util.List;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.model.Color;
import com.automundo.concesionaria.model.ImagenAutoColor;
import com.automundo.concesionaria.repositorio.AlquilerAutoRepositorio;

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
    
    @Autowired
    private AlquilerAutoRepositorio alquilerAutoRepositorio; 

   public List<AutoDTO> listarAutos() {
    return autoRepository.findAll().stream()
            .map(this::mapearAutoADTO)
            .collect(Collectors.toList());
}

    public AutoDTO buscarAutoPorId(Long idAuto) {
    Autos auto = autoRepository.findById(idAuto)
        .orElseThrow(() -> new RuntimeException("Auto no encontrado"));
    return mapearAutoADTO(auto);
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

    Autos autoGuardado = autoRepository.save(auto);

    if (autoDTO.getImagenes() != null) {
        for (ImagenAutoColorDTO imgDTO : autoDTO.getImagenes()) {
            //uso de la libreria google guava
            Color color = colorRepository.findById(imgDTO.getIdColor()).orElse(null);
            Preconditions.checkNotNull(color, "Color no encontrado para imagen");
            ImagenAutoColor imagen = new ImagenAutoColor();
            imagen.setAuto(autoGuardado);
            imagen.setColor(color);
            imagen.setNombreArchivo(imgDTO.getNombreArchivo());
            imagenAutoColorRepository.save(imagen);
        }
    }
    
     if (autoDTO.getDisponibleAlquiler() != null) {
        AlquilerAuto alquiler = new AlquilerAuto();
        alquiler.setAuto(autoGuardado);
        alquiler.setDisponibleAlquiler(autoDTO.getDisponibleAlquiler()); // "sí" o "no"
        
        alquiler.setPagoalquiler(autoDTO.getPagoalquiler());
        alquilerAutoRepositorio.save(alquiler);
    }

    // Refrescar entidad para mapear con imágenes y colores guardados
    Autos autoRefrescado = autoRepository.findById(autoGuardado.getIdAuto())
        .orElseThrow(() -> new RuntimeException("Error cargando auto insertado"));

    return mapearAutoADTO(autoRefrescado);
}
     
     
       public AutoDTO editarAuto(Long idAuto, AutoDTO autoDTO) {
    Autos autoExistente = autoRepository.findById(idAuto)
            .orElseThrow(() -> new RuntimeException("Auto no encontrado"));
    
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

    // Actualizar imágenes
    imagenAutoColorRepository.deleteAll(autoExistente.getImagenes());
    autoExistente.getImagenes().clear();

    for (ImagenAutoColorDTO imagenDTO : autoDTO.getImagenes()) {
        Color color = colorRepository.findById(imagenDTO.getIdColor()).orElse(null);
        Preconditions.checkNotNull(color, "Color no encontrado");

        ImagenAutoColor imagenAutoColor = new ImagenAutoColor();
        imagenAutoColor.setAuto(autoExistente);
        imagenAutoColor.setColor(color);
        imagenAutoColor.setNombreArchivo(imagenDTO.getNombreArchivo());

        imagenAutoColor = imagenAutoColorRepository.save(imagenAutoColor);
        autoExistente.getImagenes().add(imagenAutoColor);
    }

    Autos autoGuardado = autoRepository.save(autoExistente);
    
    if (autoDTO.getDisponibleAlquiler() != null) {
    String disponible = autoDTO.getDisponibleAlquiler().trim().toLowerCase();

    // Validar valor
    if (!disponible.equals("sí") && !disponible.equals("no")) {
        throw new IllegalArgumentException("Valor de disponibleAlquiler inválido: " + disponible);
    }

    AlquilerAuto alquiler = alquilerAutoRepositorio.findByAuto_IdAuto(autoGuardado.getIdAuto())
    .orElseGet(() -> {
        AlquilerAuto nuevo = new AlquilerAuto();
        nuevo.setAuto(autoGuardado);
        return nuevo;
    });

    alquiler.setDisponibleAlquiler(disponible); // Guardar en minúscula
    alquiler.setPagoalquiler(autoDTO.getPagoalquiler());
    alquilerAutoRepositorio.save(alquiler);
}

    // Devolver DTO actualizado mapeado desde la entidad guardada
    return mapearAutoADTO(autoGuardado);
}
       
       //Servicio que hara que listar,buscar,insertar,editar sirvan con DTO
       private AutoDTO mapearAutoADTO(Autos auto) {
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

    // Mapear colores únicos
    List<Color> colores = imagenAutoColorRepository.findColoresByAutoId(auto.getIdAuto());
    List<ColorDTO> colorDTOs = colores.stream()
            .map(c -> {
                ColorDTO cdto = new ColorDTO();
                cdto.setIdColor(c.getIdColor());
                cdto.setNombreColor(c.getNombreColor());
                return cdto;
            })
            .collect(Collectors.toList());
    dto.setColores(colorDTOs);

    // Mapear imágenes
    List<ImagenAutoColorDTO> imagenDTOs = auto.getImagenes().stream()
            .map(img -> {
                ImagenAutoColorDTO idto = new ImagenAutoColorDTO();
                idto.setIdImagen(img.getIdImagen());
                idto.setNombreArchivo(img.getNombreArchivo());
                idto.setIdColor(img.getColor().getIdColor());
                idto.setNombreColor(img.getColor().getNombreColor());
                return idto;
            })
            .collect(Collectors.toList());
    dto.setImagenes(imagenDTOs);
    
    
// Obtener disponibilidad de alquiler, en minúsculas para que el front coincida
    alquilerAutoRepositorio.findByAuto_IdAuto(auto.getIdAuto())
        .ifPresentOrElse(
            alquiler -> {dto.setDisponibleAlquiler(alquiler.getDisponibleAlquiler().toLowerCase());
                        dto.setPagoalquiler(alquiler.getPagoalquiler());
            },
            () -> 
            {dto.setDisponibleAlquiler("no disponible"); // puedes poner "no" si prefieres
                 dto.setPagoalquiler(null);
                }
                    
        );
    return dto;
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
    
    
    /* Por el momento no necesario porque ya usamos listar
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
              List<Color> colores = imagenAutoColorRepository.findColoresByAutoId(auto.getIdAuto());
        List<ColorDTO> colorDTOs = colores.stream().map(c -> {
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
    
    */
    
}