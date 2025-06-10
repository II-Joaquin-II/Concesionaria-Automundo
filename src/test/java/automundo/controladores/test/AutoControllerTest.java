package automundo.controladores.test;

import com.automundo.concesionaria.controladores.AutosController;
import com.automundo.concesionaria.dto.AutoDTO;
import com.automundo.concesionaria.servicios.AutosServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutoControllerTest {

    @InjectMocks
    private AutosController autosController;

    @Mock
    private AutosServicio autosServicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private AutoDTO crearAutoEjemplo() {
        AutoDTO auto = new AutoDTO();
        auto.setIdAuto(1L);
        auto.setModelo("Civic EX");
        auto.setMarca("Honda");
        auto.setAno(2020);
        auto.setPrecio(new BigDecimal("27,500"));
        auto.setKilometraje(15000);
        auto.setTransmision("Manual");
        auto.setCombustible("Gasolina");
        auto.setEquipamiento1("Aire acondicionado");
        auto.setCategoria("Sedan");
        auto.setEstado("Disponible");
        return auto;
    }

    @Test
    public void testListarAutos() {
        AutoDTO auto = crearAutoEjemplo();
        when(autosServicio.listarAutos()).thenReturn(List.of(auto));

        ResponseEntity<List<AutoDTO>> response = autosController.listarAutos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Honda", response.getBody().get(0).getMarca());
    }

    @Test
    public void testInsertarAuto() {
        AutoDTO auto = crearAutoEjemplo();
        when(autosServicio.insertarAuto(any(AutoDTO.class))).thenReturn(auto);

        ResponseEntity<AutoDTO> response = autosController.insertarAuto(auto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Civic EX", response.getBody().getModelo());
    }

    @Test
    public void testBuscarAuto() {
        AutoDTO auto = crearAutoEjemplo();
        when(autosServicio.buscarAutoPorId(1L)).thenReturn(auto);

        ResponseEntity<AutoDTO> response = autosController.buscarAuto(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Honda", response.getBody().getMarca());
    }

    @Test
    public void testEditarAuto() {
        AutoDTO auto = crearAutoEjemplo();
        auto.setModelo("Tesla");
        when(autosServicio.editarAuto(eq(1L), any(AutoDTO.class))).thenReturn(auto);

        ResponseEntity<AutoDTO> response = autosController.editarAuto(1L, auto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Tesla", response.getBody().getModelo());
    }

    @Test
    public void testEliminarAuto() {
        doNothing().when(autosServicio).eliminarAuto(1L);

        ResponseEntity<Void> response = autosController.eliminarAuto(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(autosServicio, times(1)).eliminarAuto(1L);
    }
}
