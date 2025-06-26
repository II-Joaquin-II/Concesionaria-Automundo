
package com.automundo.concesionaria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "alquiler_auto")
public class AlquilerAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alquiler")
    private Long idAlquiler;
    
     @Column(name = "pago_alquiler")
     private BigDecimal pagoalquiler;
    
    @OneToOne
    @JoinColumn(name = "id_auto", referencedColumnName = "id_auto")
    private Autos auto;

    @Column(name = "disponible_alquiler")
    private String disponibleAlquiler; // "sí" o "no"
}