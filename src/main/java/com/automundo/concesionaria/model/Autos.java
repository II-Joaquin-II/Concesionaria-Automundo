
package com.automundo.concesionaria.model;

import jakarta.persistence.*;  
import java.math.BigDecimal;

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
    private Integer idAuto;

    private String modelo;
    private String color;
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
    private String imagen;
    private String categoria;
    private String estado;
   
}