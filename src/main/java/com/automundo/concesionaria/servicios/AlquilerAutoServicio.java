
package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.dto.AlquilerAutoDTO;
import com.automundo.concesionaria.model.AlquilerAuto;
import com.automundo.concesionaria.repositorio.AlquilerAutoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlquilerAutoServicio {

    private final AlquilerAutoRepositorio alquilerAutoRepository;

    public Optional<AlquilerAutoDTO> obtenerDisponibilidadPorAutoId(Long idAuto) {
        return alquilerAutoRepository.findByAuto_IdAuto(idAuto).map(this::convertirADTO);
    }

    private AlquilerAutoDTO convertirADTO(AlquilerAuto alquiler) {
        AlquilerAutoDTO dto = new AlquilerAutoDTO();
        dto.setIdAlquiler(alquiler.getIdAlquiler());
        dto.setIdAuto(alquiler.getAuto().getIdAuto());
        dto.setDisponibleAlquiler(alquiler.getDisponibleAlquiler());
        return dto;
    }
}
