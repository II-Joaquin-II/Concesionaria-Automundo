/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automundo.controladores.test;

/**
 *
 * @author CRIKROSS
 */
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;
import com.automundo.concesionaria.servicios.UsuarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioService usuarioService;

    // prueba unitariaa para obtener usuario por email exitoso
    @Test
    public void testObtenerUsuarioPorEmail_exitoso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@gmail.com");
        usuario.setNombre_usuario("Joaquin");

        when(usuarioRepositorio.findByEmail("test@gmail.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorEmail("test@gmail.com");

        assertNotNull(resultado);
        assertEquals("Joaquin", resultado.getNombre_usuario());
    }
  
    // prueba unitaria para obtener usuario por email no encontrado
    @Test
    public void testObtenerUsuarioPorEmail_noEncontrado() {
        when(usuarioRepositorio.findByEmail("noexiste@gmail.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.obtenerUsuarioPorEmail("noexiste@gmail.com");
        });

        assertEquals("Correo no encontrado: noexiste@gmail.com", exception.getMessage());
    }
}
