package com.automundo.concesionaria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "imagen_auto_color")
public class ImagenAutoColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagen;

    @ManyToOne
    @JoinColumn(name = "id_auto", nullable = false)
    /*@JsonManagedReference*/ //para quitar apis infinitos
    private Autos auto;
    
     
    @ManyToOne
    @JoinColumn(name = "id_color", nullable = false)
    private Color color;
     
    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    
   
    @JsonProperty("urlImagen")
    public String getUrlImagen() {
    return "/img/" + nombreArchivo; // solo ruta relativa
}
    
}