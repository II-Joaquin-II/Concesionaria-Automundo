
package com.automundo.concesionaria.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data

public class AutoDTO {
    
    private Long idAuto;
    private String modelo;
    private String marca;
    private int ano;
    private BigDecimal precio;
    private int kilometraje;
    private String transmision;
    private String combustible;
    private String equipamiento1;
    private String equipamiento2;
    private String equipamiento3;
    private String equipamiento4;
    private String categoria;
    private String estado;

    private List<ColorDTO> colores;
    private List<ImagenAutoColorDTO> imagenes;
    
    
}