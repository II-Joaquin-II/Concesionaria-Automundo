
package com.automundo.concesionaria.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clientes")

public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cli;

    private String nombre_cli;
    private String apellidos_cli;
    private String dni;
    private LocalDate fecha_nac;
    private String celular;
    private String email;
    private String usuario_cli;
    private String pass_cli;
    
}
