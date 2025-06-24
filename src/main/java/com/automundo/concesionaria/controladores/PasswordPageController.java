package com.automundo.concesionaria.controladores;

import com.automundo.concesionaria.servicios.PasswordResetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PasswordPageController {

    private final PasswordResetService passwordResetService;

    @Autowired
    PasswordPageController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    /* 
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        passwordResetService.sendResetLink(email);
        model.addAttribute("message", true);
        return "forgot-password";
    }
    */

    
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> processForgotPassword(@RequestParam String email) {
    passwordResetService.sendResetLink(email);
    return ResponseEntity.ok().build(); // importante para que JS detecte que todo salió bien
    }
    


    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword,
            Model model) {

        if (passwordResetService.validateToken(token)) {
            passwordResetService.updatePassword(token, newPassword);
            model.addAttribute("message", "Tu contraseña fue actualizada con éxito.");
        } else {
            model.addAttribute("message", "El token es inválido o ha expirado.");
        }

        return "reset-password";
    }

}
