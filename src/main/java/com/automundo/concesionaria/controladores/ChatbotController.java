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

    private static final String API_KEY = "AIzaSyCSWB0W4wS8UL-PXorRnK_y6Gv4wWZCTDM";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        try {
            String mensajeUsuario = request.get("message").trim().toLowerCase();

            // MENÚ inicial
            if (mensajeUsuario.matches(".*\\b(necesito ayuda|buenas)\\b.*")) {
                String menuOpciones = """
                    Descubre Nuestras Opciones 😎

                    Escribe el número de la opción de tu interés para brindarte la información que buscas 👇

                    1: Alquilar un auto 🚗
                    2: Comprar un auto 🛒
                    3: Mostrar detalle de un vehículo 🔍
                    4: Contactos 📞
                """;
                return Map.of("reply", menuOpciones);
            }

            // RESPUESTAS a opciones 1-4
            switch (mensajeUsuario) {
                case "1" -> {
                    return Map.of("reply", "¡Genial! Tenemos autos disponibles para alquiler desde USD $500 por día. Requisitos: DNI, brevete y tarjeta de crédito. Por favor, ingresa tu DNI para continuar.");
                }
                case "2" -> {
                    return Map.of("reply", "¡Perfecto! Puedes comprar autos nuevos o seminuevos. Consulta nuestro catálogo o agenda una cita. ¿Deseas agendar una?");
                }
                case "3" -> {
                    return Map.of("reply", "Indícanos el modelo del vehículo que deseas consultar. Por ejemplo: *Mercedes-Benz S-Class S580*.");
                }
                case "4" -> {
                    return Map.of("reply", "Puedes comunicarte al 📞 *+51 925 222 579* o vía WhatsApp al mismo número. ¡Estamos para ayudarte!");
                }
            }

            // RESPUESTA si ingresa un DNI (8 dígitos)
            if (mensajeUsuario.matches("\\d{8}")) {
                return Map.of("reply", "Nos comunicaremos contigo pronto, vía e-mail.");
            }

            // RESPUESTA si dice "sí"(confirmación)
            if (mensajeUsuario.equals("sí") || mensajeUsuario.equals("si")) {
                return Map.of("reply", "Excelente, nos pondremos en contacto contigo en las próximas horas.");
            }

            // RESPUESTA para el nombre del auto específico (Por ahora este modelo)
            if (mensajeUsuario.contains("mercedes-benz s-class s580")) {
                return Map.of("reply", "Precio: USD 32,700.");
            }

            // Consulta general a Gemini (por defecto)
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
