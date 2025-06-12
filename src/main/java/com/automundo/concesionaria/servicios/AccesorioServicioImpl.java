package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.dao.AccesorioDAO;
import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.repositorio.AccesorioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class AccesorioServicioImpl implements AccesorioServicio {

    @Autowired
    private AccesorioDAO dao;

    @Override
    public List<Accesorio> listarTodos() {
        return dao.listarTodos();
    }

    @Override
    public Accesorio guardar(Accesorio accesorio) {
        return dao.guardar(accesorio);
    }

    @Override
    public Accesorio obtenerPorId(Long id) {
        return dao.obtenerPorId(id);
    }

    @Override
    public void eliminar(Long id) {
        dao.eliminar(id);
    }

    @Override
    public List<Accesorio> buscarPorNombre(String nombre) {
        return dao.buscarPorNombre(nombre);
    }
}
