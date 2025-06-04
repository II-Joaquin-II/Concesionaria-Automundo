package com.automundo.concesionaria.dto;

import java.math.BigDecimal;

import com.automundo.concesionaria.model.Autos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutosDTO {

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

    public AutosDTO(Autos auto) {
        this.idAuto = auto.getIdAuto();
        this.modelo = auto.getModelo();
        this.color = auto.getColor();
        this.marca = auto.getMarca();
        this.ano = auto.getAno();
        this.precio = auto.getPrecio();
        this.kilometraje = auto.getKilometraje();
        this.transmision = auto.getTransmision();
        this.combustible = auto.getCombustible();
        this.equipamiento1 = auto.getEquipamiento1();
        this.equipamiento2 = auto.getEquipamiento2();
        this.equipamiento3 = auto.getEquipamiento3();
        this.equipamiento4 = auto.getEquipamiento4();
        this.imagen = auto.getImagen();
        this.categoria = auto.getCategoria();
        this.estado = auto.getEstado();
    }

}