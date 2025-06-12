
package com.automundo.concesionaria.dto;

import lombok.Data;


@Data

public class ImagenAutoColorDTO {
    private Long idImagen;
    private String nombreArchivo;
    private Long idColor;
    private String nombreColor;
}