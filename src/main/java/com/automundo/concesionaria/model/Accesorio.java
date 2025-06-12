package com.automundo.concesionaria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accesorio")
public class Accesorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_acc;

    private String nombre;
    private String descripcion;
    private String imagen; // imagen por defecto
    private String colores; // separados por coma
    private double precio;

    // Getters y Setters
    public Long getId_acc() {
        return id_acc;
    }

    public void setId_acc(Long id_acc) {
        this.id_acc = id_acc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getColores() {
        return colores;
    }

    public void setColores(String colores) {
        this.colores = colores;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
