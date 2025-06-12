
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
    public List<Accesorio> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Accesorio guardar(Accesorio accesorio) {
        return repo.save(accesorio);
    }

    @Override
    public Accesorio obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Accesorio> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
}
