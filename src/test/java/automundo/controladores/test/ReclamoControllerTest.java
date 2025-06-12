package automundo.controladores.test;

import com.automundo.concesionaria.controladores.ReclamoController;
import com.automundo.concesionaria.dto.ReclamoDTO;
import com.automundo.concesionaria.model.Reclamo;
import com.automundo.concesionaria.repositorio.ReclamoRepositorio;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;
import com.automundo.concesionaria.servicios.ReclamoServicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReclamoControllerTest {

    @InjectMocks
    private ReclamoController reclamoController;

    @Mock
    private ReclamoServicio reclamoServicio;

    @Mock
    private ReclamoRepositorio reclamoRepositorio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearReclamo_UsuarioNoEncontrado() {
        ReclamoDTO dto = new ReclamoDTO();
        dto.setMotivoReclamo("No arranca");

        UserDetails userDetails = User.withUsername("noexiste@gmail.com")
                .password("123")
                .roles("USER")
                .build();

        when(usuarioRepositorio.findByEmail("noexiste@gmail.com")).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = reclamoController.crearReclamo(dto, userDetails);

        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Usuario no encontrado", respuesta.getBody());

        verify(reclamoRepositorio, never()).save(any());
    }

    @Test
    public void testListarReclamos() {
        Reclamo r1 = new Reclamo();
        r1.setMotivoReclamo("Motor");
        Reclamo r2 = new Reclamo();
        r2.setMotivoReclamo("Batería");

        when(reclamoServicio.listarReclamos()).thenReturn(List.of(r1, r2));

        List<Reclamo> resultado = reclamoController.listarReclamo();

        assertEquals(2, resultado.size());
        assertEquals("Motor", resultado.get(0).getMotivoReclamo());
    }

    @Test
    public void testBuscarReclamoPorId_Existe() {
        Reclamo r = new Reclamo();
        r.setIdReclamo(1); 
        r.setMotivoReclamo("Frenos");

        when(reclamoServicio.buscarReclamoPorId(1)).thenReturn(Optional.of(r));

        ResponseEntity<Reclamo> resp = reclamoController.buscarReclamoPorId(1);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("Frenos", resp.getBody().getMotivoReclamo());
    }

    @Test
    public void testBuscarReclamoPorId_NoExiste() {
        when(reclamoServicio.buscarReclamoPorId(99)).thenReturn(Optional.empty());

        ResponseEntity<Reclamo> resp = reclamoController.buscarReclamoPorId(99);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
    }

    @Test
    public void testEliminarReclamo() {
        ResponseEntity<String> resp = reclamoController.eliminarReclamo(3);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("Reclamo eliminado correctamente", resp.getBody());

        verify(reclamoServicio).eliminarReclamo(3);
    }
}
