package com.automundo.concesionaria.seguridad;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.automundo.concesionaria.model.Clientes;
import com.automundo.concesionaria.repositorio.ClientesRepositorio;

@Configuration
@EnableWebSecurity
public class SecurityClass {

    @Autowired
    private ClientesRepositorio repo_clientes;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService uds) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(uds)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/registro", "/principal", "/admin", "/adminverclientes", "/index")
                .permitAll()

                .requestMatchers(HttpMethod.POST, "/api/clientes")
                .permitAll()
                
                //.requestMatchers("/carousel.css", "/sweetalert.js", "/img/**", "/login.js", "/loginregistro.css", "/estiloAdmin.css", "/registro.js")
                //.permitAll()
                
                .requestMatchers("/css/**", "/js/**", "/img/**")
                .permitAll()
                
                //.requestMatchers("/admin/**").hasRole("ADMIN")

                .requestMatchers("/detallesauto", "/comprar").hasRole("USER")

                .anyRequest().authenticated() 

            )
            .formLogin(form -> form
                .loginPage("/login") 
                .permitAll()
                .defaultSuccessUrl("/principal", true)
                .failureUrl("/login?error=true")
            )
            
            .logout(logout -> logout
                .permitAll()
                .logoutSuccessUrl("/login")  
            );

        return http.build();
    }

    //Encripta las contraseñas de la base de datos de la tabla clientes
    @Bean
    public CommandLineRunner encryptPasswords() {
    return args -> {
        List<Clientes> clientes = repo_clientes.findAll();

        for (Clientes cliente : clientes) {
            String rawPassword = cliente.getPass_cli(); 
            if (!rawPassword.startsWith("$2a$")) { 
                String encrypted = passwordEncoder().encode(rawPassword);
                cliente.setPass_cli(encrypted);
                repo_clientes.save(cliente);
            }
        }
    };
    }



}
