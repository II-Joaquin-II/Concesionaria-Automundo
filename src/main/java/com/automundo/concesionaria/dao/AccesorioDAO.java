package com.automundo.concesionaria.dao;

import com.automundo.concesionaria.model.Accesorio;

import java.util.List;

public interface AccesorioDAO {

    List<Accesorio> getAll();

    Accesorio getById(Long id);

    void save(Accesorio accesorio);

    void update(Accesorio accesorio);

    void delete(Long id);
}
