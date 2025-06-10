package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.dao.AccesorioDAO;
import com.automundo.concesionaria.model.Accesorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccesorioServicioImpl implements AccesorioServicio {

    @Autowired
    private AccesorioDAO dao;

    @Transactional(readOnly = true)
    @Override
    public List<Accesorio> listAll() {
        return dao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Accesorio findById(Long id) {
        return dao.getById(id);
    }

    @Transactional
    @Override
    public void save(Accesorio accesorio) {
        dao.save(accesorio);
    }

    @Transactional
    @Override
    public void update(Accesorio accesorio) {
        dao.update(accesorio);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
}
