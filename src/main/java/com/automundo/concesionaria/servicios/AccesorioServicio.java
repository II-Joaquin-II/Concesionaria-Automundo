package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.model.Accesorio;

import java.util.List;

public interface AccesorioServicio {

    List<Accesorio> listAll();

    Accesorio findById(Long id);

    void save(Accesorio accesorio);

    void update(Accesorio accesorio);

    void delete(Long id);
}
