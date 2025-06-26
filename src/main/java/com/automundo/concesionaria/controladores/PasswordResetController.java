package com.automundo.concesionaria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.automundo.concesionaria.servicios.PasswordResetService;

@RestController
public class PasswordResetController {
    
    @Autowired
    private PasswordResetService resetService;

    @PostMapping("/api/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        resetService.sendResetLink(email);
        return ResponseEntity.ok("Correo de restablecimiento enviado.");
    }

    @PostMapping("/api/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (resetService.validateToken(token)) {
            resetService.updatePassword(token, newPassword);
            return ResponseEntity.ok("Contraseña actualizada.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido o expirado.");
        }
    }

}
