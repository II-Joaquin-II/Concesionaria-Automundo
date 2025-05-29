package com.automundo.concesionaria.dto;

import java.time.LocalDate;
import com.automundo.concesionaria.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private Integer id_usuario;
    private String nombre_usuario;
    private String apellidos_usuario;
    private String dni;
    private LocalDate fecha_nac;
    private String celular;
    private String email;
    private String usuario;
    private String pass;

    public UsuarioDTO(Usuario usuarioEntidad) {
        this.id_usuario = usuarioEntidad.getId_usuario();
        this.nombre_usuario = usuarioEntidad.getNombre_usuario();
        this.apellidos_usuario = usuarioEntidad.getApellidos_usuario();
        this.dni = usuarioEntidad.getDni();
        this.fecha_nac = usuarioEntidad.getFecha_nac();
        this.celular = usuarioEntidad.getCelular();
        this.email = usuarioEntidad.getEmail();
        this.usuario = usuarioEntidad.getUsuario();
        this.pass = usuarioEntidad.getPass();
    }
}
