package com.automundo.concesionaria.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /*public void agregarItem(Item nuevo) {
        for (Item item : items) {
            if (item.getId().equals(nuevo.getId())) {
                return;
            }
        }
        items.add(nuevo);
    }*/

    public void agregarItem(Item nuevo) {
    for (Item item : items) {
        if (item.getId().equals(nuevo.getId())) {
            // Si es el mismo ID pero distinto color, actualizar color y precio
            item.setColor(nuevo.getColor());
            item.setPrecio(nuevo.getPrecio()); // por si el precio cambia
            return;
        }
    }
    items.add(nuevo);
}
    
    public void eliminarItem(Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public double getTotal() {
        return items.stream().mapToDouble(Item::getPrecio).sum();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }    
    public void vaciar() {
        items.clear();
    }
}
