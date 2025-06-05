package com.automundo.concesionaria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Accesorio {
    private String nombre;
    private String precio;
    private String imagen;

    @JsonProperty("coloresDisponibles")
    private Map<String, String> imagenes_colores;

    @JsonIgnore
    private String compatibilidad;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descripcion;

    public Accesorio() {}

    public Accesorio(String nombre, String precio, String imagen, Map<String, String> imagenes_colores,
                     String compatibilidad, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.imagenes_colores = imagenes_colores;
        this.compatibilidad = compatibilidad;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Map<String, String> getImagenes_colores() { return imagenes_colores; }
    public void setImagenes_colores(Map<String, String> imagenes_colores) { this.imagenes_colores = imagenes_colores; }

    public String getCompatibilidad() { return compatibilidad; }
    public void setCompatibilidad(String compatibilidad) { this.compatibilidad = compatibilidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}