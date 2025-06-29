package com.automundo.concesionaria.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class EmailDTO {

    private String para;
    private String asunto;
    private String mensaje;

    
    public EmailDTO(String para, String asunto, String mensaje) {
        this.para = para;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }
    

    
}
