package com.automundo.concesionaria.controladores;


import com.automundo.concesionaria.dto.ColorDTO;
import com.automundo.concesionaria.dto.ImagenAutoColorDTO;
import com.automundo.concesionaria.model.Color;
import com.automundo.concesionaria.model.ImagenAutoColor;
import com.automundo.concesionaria.repositorio.ImagenAutoColorRepositorio;
import com.automundo.concesionaria.servicios.ImagenAutoColorServicio;
import java.io.File;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenAutoColorControlador {

    
     @Autowired
    private ImagenAutoColorServicio imagenServicio;

  @GetMapping
    public ResponseEntity<List<ImagenAutoColorDTO>> listarImagenes() {
        List<ImagenAutoColorDTO> imagenes = imagenServicio.listarTodas();
        return ResponseEntity.ok(imagenes);
    }
    
    /*
     @GetMapping("/api/imagenes")
    public List<String> getImagenes() {
        // Ruta de la carpeta donde están las imágenes
        String rutaCarpeta = "src/main/resources/static/img/";
        File carpeta = new File(rutaCarpeta);
        File[] archivos = carpeta.listFiles();
        
        List<String> imagenes = new ArrayList<>();
        
        // Filtrar solo los archivos con extensión .jpg, .png, etc.
        for (File archivo : archivos) {
            if (archivo.isFile() && (archivo.getName().endsWith(".jpg") || archivo.getName().endsWith(".png"))) {
                imagenes.add(archivo.getName());
            }
        }
        
        return imagenes; // Devolver la lista de nombres de archivos
    }
*/

    @PostMapping
    public ImagenAutoColor agregar(@RequestBody ImagenAutoColor imagen) {
        return imagenServicio.save(imagen);
    }
    
    //para poder ver el api de auto con sus colores solos con DTO
      @GetMapping("/{idAuto}/colores")
    public List<ColorDTO> obtenerColoresPorAuto(@PathVariable Long idAuto) {
        List<Color> colores = imagenServicio.obtenerColoresPorAutoId(idAuto);

        // Convertir Color a ColorDTO
        return colores.stream().map(c -> {
            ColorDTO dto = new ColorDTO();
            dto.setIdColor(c.getIdColor());
            dto.setNombreColor(c.getNombreColor());
            return dto;
        }).toList();
    }

   @DeleteMapping("/{id}")
public ResponseEntity<?> eliminar(@PathVariable Long id) {
    imagenServicio.delete(id);
    return ResponseEntity.ok().build();
}
}