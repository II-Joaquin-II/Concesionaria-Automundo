package com.automundo.concesionaria.servicios;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.PasswordResetToken;
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.PasswordResetTokenRepository;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired
    private UsuarioRepositorio userRepo;

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetLink(String email) {
        Usuario user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        tokenRepo.deleteByUser(user);
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusMinutes(30));
        tokenRepo.save(resetToken);

        String resetUrl = "http://localhost:8081/reset-password?token=" + token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset your password");
        mailMessage.setText("Click here to reset your password: " + resetUrl);

        //mailSender.send(mailMessage);

        try {
        mailSender.send(mailMessage);
        System.out.println("### CORREO ENVIADO EXITOSAMENTE ###");
    } catch (Exception e) {
        System.out.println("### ERROR AL ENVIAR CORREO ###");
        e.printStackTrace();
    }
    
    }

    public boolean validateToken(String token) {
        PasswordResetToken prt = tokenRepo.findByToken(token).orElse(null);
        return prt != null && prt.getExpiryDate().isAfter(LocalDateTime.now());
    }

    public void updatePassword(String token, String newPassword) {
        PasswordResetToken prt = tokenRepo.findByToken(token).orElseThrow();
        Usuario user = prt.getUser();
        user.setPass(new BCryptPasswordEncoder().encode(newPassword));
        userRepo.save(user);
        tokenRepo.delete(prt); 
    }


}
