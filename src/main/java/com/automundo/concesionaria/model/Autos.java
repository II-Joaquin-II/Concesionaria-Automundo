
package com.automundo.concesionaria.model;


import jakarta.persistence.*;  
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "autos")
public class Autos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auto")
    private Long  idAuto;

    private String modelo;
    private String marca;
    
    private Integer ano;

    private BigDecimal precio;
    private Integer kilometraje;
    private String transmision;
    private String combustible;
    private String equipamiento1;
    private String equipamiento2;
    private String equipamiento3;
    private String equipamiento4;
    private String categoria;
    private String estado;
    
     @ManyToMany
    @JoinTable(
        name = "color_auto",
        joinColumns = @JoinColumn(name = "id_auto"),
        inverseJoinColumns = @JoinColumn(name = "id_color")
    )
     private List<Color> colores = new ArrayList<>();
   
    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenAutoColor> imagenes = new ArrayList<>();
   
   
}