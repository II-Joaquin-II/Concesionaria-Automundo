/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.automundo.concesionaria.controladores;

/**
 *
 * @author CRIKROSS
 */
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.net.http.*;
import java.net.URI;
import org.json.JSONObject;

@RestController
public class ChatbotController {

    private static final String API_KEY = "AIzaSyCSWB0W4wS8UL-PXorRnK_y6Gv4wWZCTDM";  // ←clave de Gemini
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        try {
            String mensajeUsuario = request.get("message").toLowerCase().trim();

            // Detectar saludo
            if (mensajeUsuario.matches(".*\\b(necesito informacion|buenas)\\b.*")) {
                String menuOpciones = """
            Descubre Nuestras Opciones 😎

            Escribe el número de la opción de tu interés para brindarte la información que buscas 👇

                                                  1: Alquilar un auto
                                                              2: Comprar un auto
                                                              3: Mostrar detalle de un vehículo
                                                              4: Contactos
           
            """;
                return Map.of("reply", menuOpciones);
            }

            // Detectar si elige opción 4
            if (mensajeUsuario.equals("4")) {
                String contacto = "Puuedes comunicarte al 📞 *+51 925 222 579* o vía WhatsApp al +51 925 222 579.";
                return Map.of("reply", contacto);
            }

            // Lógica normal con Gemini
            String json = """
        {
          "contents": [
            {
              "parts": [
                {
                  "text": "%s"
                }
              ]
            }
          ]
        }
        """.formatted(mensajeUsuario);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return Map.of("reply", "Error al comunicarse con Gemini.");
            }

            JSONObject obj = new JSONObject(response.body());
            String respuesta = obj.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            return Map.of("reply", respuesta);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("reply", "Error interno del servidor.");
        }
    }

}
