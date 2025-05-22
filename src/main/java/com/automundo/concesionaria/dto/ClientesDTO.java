package com.automundo.concesionaria.dto;

import java.time.LocalDate;
import com.automundo.concesionaria.model.Clientes;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ClientesDTO {

    private Integer id_cli;
    private String nombre_cli;
    private String apellidos_cli;
    private String dni;
    private LocalDate fecha_nac;
    private String celular;
    private String email;
    private String usuario_cli;
    private String pass_cli;

    public ClientesDTO(Clientes p_clientes) {
        this.id_cli = p_clientes.getId_cli();
        this.nombre_cli = p_clientes.getNombre_cli();
        this.apellidos_cli = p_clientes.getApellidos_cli();
        this.dni = p_clientes.getDni();
        this.fecha_nac = p_clientes.getFecha_nac();
        this.celular = p_clientes.getCelular();
        this.email = p_clientes.getEmail();
        this.usuario_cli = p_clientes.getUsuario_cli();
        this.pass_cli = p_clientes.getPass_cli();
    }


}
