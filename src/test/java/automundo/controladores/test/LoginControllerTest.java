package automundo.controladores.test;

import com.automundo.concesionaria.controladores.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Test
    public void testMostrarLogin() {
        LoginController controller = new LoginController();
        Model model = mock(Model.class);

        String vista = controller.Login(model);

        assertEquals("login", vista);
    }
}
