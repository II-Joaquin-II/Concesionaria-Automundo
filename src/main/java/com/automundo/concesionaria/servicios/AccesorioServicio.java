
package com.automundo.concesionaria.servicios;
import com.automundo.concesionaria.model.Accesorio;
import java.util.List;

public interface AccesorioServicio {
    List<Accesorio> listarTodos();
    Accesorio guardar(Accesorio accesorio);
    Accesorio obtenerPorId(Long id);
    void eliminar(Long id);
    List<Accesorio> buscarPorNombre(String nombre);
}

