package automundo.controladores.test;

import com.automundo.concesionaria.model.Color;
import com.automundo.concesionaria.repositorio.ColorRepositorio;
import com.automundo.concesionaria.servicios.ColorServicio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColorServicioTest {

    @Mock
    private ColorRepositorio colorRepositorio;

    @InjectMocks
    private ColorServicio colorServicio;

    @Test
    public void testListarTodos() {
        Color color1 = new Color();
        color1.setNombreColor("Rojo");
        Color color2 = new Color();
        color2.setNombreColor("Azul");
  
        when(colorRepositorio.findAll()).thenReturn(Arrays.asList(color1, color2));

        List<Color> colores = colorServicio.getAll();

        assertEquals(2, colores.size());
        assertEquals("Rojo", colores.get(0).getNombreColor());
        assertEquals("Azul", colores.get(1).getNombreColor());
    }
}
