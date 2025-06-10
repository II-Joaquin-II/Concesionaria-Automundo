/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automundo.controladores.test;

import com.automundo.concesionaria.controladores.ChatbotController;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ChatbotControllerTest {
    
    //sin usar mock para chatbot
    
    @Test
    public void testChatRespuesta() {
        ChatbotController controller = new ChatbotController();

        Map<String, String> respuesta = controller.chat(Map.of("message", "necesito informacion"));

        assertNotNull(respuesta.get("reply"));
        assertTrue(respuesta.get("reply").length() > 0);
        System.out.println("Respuesta del chatbot: " + respuesta.get("reply"));
    }
}
