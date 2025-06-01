package com.automundo.concesionaria.dto;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReclamoDTO {

    private Integer idReclamo;
    private Long idCliente;
    private String fechaincidente;
    private String motivoReclamo;
    private String tipo_Vehiculo;
    private String detalle;
    private String estadoReclamo;
    private Timestamp fechaReclamo;

    public ReclamoDTO(Integer idReclamo, Long idCliente, String fechaincidente, String motivoReclamo, String tipo_Vehiculo, String detalle, String estadoReclamo, Timestamp fechaReclamo) {
        this.idReclamo = idReclamo;
        this.idCliente = idCliente;
        this.fechaincidente = fechaincidente;
        this.motivoReclamo = motivoReclamo;
        this.tipo_Vehiculo = tipo_Vehiculo;
        this.detalle = detalle;
        this.estadoReclamo = estadoReclamo;
        this.fechaReclamo = fechaReclamo;
    }
}
