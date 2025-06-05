package com.automundo.concesionaria.controladores;
import com.automundo.concesionaria.model.Accesorio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AccesorioController {

    @GetMapping("/api/accesorios")
    public List<Accesorio> getAccesorios() {
        return List.of(
            new Accesorio(
                "Asiento Deportivo",
                "120.00",
                "/img/asiento-rojo.jpg",
                Map.of(
                    "rojo", "/img/asiento-rojo.jpg",
                    "negro", "/img/asiento-negro.jpg",
                    "azul", "/img/asiento-azul.jpg"
                ),
                "Vehículos deportivos",
                "Asiento ergonómico para mejorar la comodidad durante la conducción."
            ),
            new Accesorio(
                "Funda para Volante",
                "30.00",
                "/img/volante-rojo.jpg",
                Map.of(
                    "rojo", "/img/volante-rojo.jpg",
                    "negro", "/img/volante-negro.jpg",
                    "azul", "/img/volante-azul.jpg"
                ),
                "Todos los vehículos",
                "Funda de alta calidad para proteger tu volante y mejorar el agarre."
            ),
            new Accesorio(
                "Llantas All-Terrain",
                "250.00",
                "/img/llanta-roja.jpeg",
                Map.of(
                    "rojo", "/img/llanta-roja.jpeg",
                    "negro", "/img/llanta-negra.jpg",
                    "azul", "/img/llanta-azul.jpg"
                ),
                "SUVs y 4x4",
                "Llantas para terrenos difíciles y condiciones extremas."
            ),
            new Accesorio(
                "Faro LED",
                "70.00",
                "/img/faro-rojo.jpg",
                Map.of(
                    "rojo", "/img/faro-rojo.jpg",
                    "negro", "/img/faro-negro.jpg",
                    "azul", "/img/faro-azul.jpg"
                ),
                "Vehículos de todo tipo",
                "Faro LED de alta potencia para mejorar la visibilidad nocturna."
            ),
            new Accesorio(
                "Alarma de Seguridad",
                "80.00",
                "/img/alarma-roja.jpeg",
                Map.of(
                    "rojo", "/img/alarma-roja.jpeg",
                    "negro", "/img/alarma-negra.jpeg",
                    "azul", "/img/alarma-azul.jpg"
                ),
                "Vehículos con sistema eléctrico",
                "Sistema de alarma avanzado para mayor seguridad."
            ),
            new Accesorio(
                "Sistema de Sonido",
                "300.00",
                "/img/sonido-rojo.jpg",
                Map.of(
                    "rojo", "/img/sonido-rojo.jpg",
                    "negro", "/img/sonido-negro.jpeg",
                    "azul", "/img/sonido-azul.jpeg"
                ),
                "Vehículos con sistema de audio",
                "Sistema de sonido premium para una experiencia de audio superior."
            )
        );
    }
}