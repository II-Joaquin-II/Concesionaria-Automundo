
package com.automundo.concesionaria.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AlquilerAutoDTO {
    private Long idAlquiler;
    private Long idAuto;
    private String disponibleAlquiler;
      private BigDecimal pagoalquiler;
}