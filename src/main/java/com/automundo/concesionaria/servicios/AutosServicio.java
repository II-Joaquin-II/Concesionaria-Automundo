
package com.automundo.concesionaria.servicios;
import java.util.List;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.repositorio.AutosRepositorio;
import java.util.Optional;


@Service
public class AutosServicio {
     private final AutosRepositorio autoRepo;

    public AutosServicio(AutosRepositorio autorepositorio) {
        this.autoRepo = autorepositorio;
    }

    public List<Autos> listarAutos() {
        return autoRepo.findAll();
    }
    
    public void insertarAuto(Autos auto) {
        autoRepo.save(auto);
    }
    
public Optional<Autos> buscarAutoPorId(Integer id) {
    return autoRepo.findById(id);
}
    
public void eliminarAuto(Integer id) {
    autoRepo.deleteById(id);
    }
    
}
