package com.automundo.concesionaria.dao;

import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.repositorio.AccesorioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccesorioDAOImpl implements AccesorioDAO {

    @Autowired
    private AccesorioRepositorio repo;

    @Override
    public List<Accesorio> getAll() {
        return repo.findAll();
    }

    @Override
    public Accesorio getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void save(Accesorio accesorio) {
        repo.save(accesorio);
    }

    @Override
    public void update(Accesorio accesorio) {
        repo.save(accesorio);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}