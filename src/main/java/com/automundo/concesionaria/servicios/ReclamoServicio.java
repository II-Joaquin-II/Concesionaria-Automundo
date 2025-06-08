package com.automundo.concesionaria.servicios;

import java.util.List;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Reclamo;
import com.automundo.concesionaria.repositorio.ReclamoRepositorio;
import java.util.Optional;

@Service
public class ReclamoServicio {
    private final ReclamoRepositorio reclamorepo;
    
    public ReclamoServicio(ReclamoRepositorio reclamorepositorio) {
        this.reclamorepo = reclamorepositorio;
    }

    public List<Reclamo> listarReclamos() {
        return reclamorepo.findAll();
    }

    public void insertarReclamo(Reclamo reclamo) {
        reclamorepo.save(reclamo);
    }

    public Optional<Reclamo> buscarReclamoPorId(Integer id) {
        return reclamorepo.findById(id);
    }

    public void eliminarReclamo(Integer id) {
        reclamorepo.deleteById(id);
    }
}
