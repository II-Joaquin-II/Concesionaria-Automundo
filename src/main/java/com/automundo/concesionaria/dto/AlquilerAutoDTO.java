
package com.automundo.concesionaria.dto;

import lombok.Data;

@Data
public class AlquilerAutoDTO {
    private Long idAlquiler;
    private Long idAuto;
    private String disponibleAlquiler;
}