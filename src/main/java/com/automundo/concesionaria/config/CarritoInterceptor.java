package com.automundo.concesionaria.config;

import com.automundo.concesionaria.model.Carrito;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CarritoInterceptor implements HandlerInterceptor {

    
    @Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession(false);
    if (session != null) {
        String uri = request.getRequestURI();

        // Páginas donde SÍ se debe vaciar el carrito
        if (uri.equals("/") || uri.contains("/index") || uri.contains("/infoauto")) {
            Carrito carrito = (Carrito) session.getAttribute("carrito");
            if (carrito != null) {
                carrito.vaciar();
            }
            session.setAttribute("enProcesoPago", false);
            return true;
        }

        // En otras páginas del flujo de compra NO se vacía
        List<String> rutasFlujoCompra = List.of("/pagos", "/carrito", "/confirmacion", "/css", "/js", "/imagenes");
        boolean esFlujoCompra = rutasFlujoCompra.stream().anyMatch(uri::contains);

        if (!esFlujoCompra) {
            Boolean enProcesoPago = (Boolean) session.getAttribute("enProcesoPago");
            if (Boolean.TRUE.equals(enProcesoPago)) {
                session.setAttribute("enProcesoPago", false);
            }
        }
    }
    return true;
}

}
