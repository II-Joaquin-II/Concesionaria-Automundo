package com.automundo.concesionaria.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {

    private Long id;
    private String nombre;
    private double precio;
    private String color;

    public Item(Long id, String nombre, double precio, String color) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.color = color;
    }

}
