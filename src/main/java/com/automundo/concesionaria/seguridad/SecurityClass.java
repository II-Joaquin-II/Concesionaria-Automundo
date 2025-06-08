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
import com.automundo.concesionaria.model.Usuario;
import com.automundo.concesionaria.repositorio.UsuarioRepositorio;

@Configuration
@EnableWebSecurity
public class SecurityClass {

    @Autowired
    private UsuarioRepositorio repo_clientes;

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
                .requestMatchers("/login", "/registro", "/principal", "/index")
                .permitAll()
             
                .requestMatchers(HttpMethod.GET, "/api/autos").permitAll()
                    
                .requestMatchers(HttpMethod.POST, "/api/clientes")
                .permitAll()

                .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasRole("ADMIN")

                .requestMatchers("/css/**", "/js/**", "/img/**")
                .permitAll()
                
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .requestMatchers("/detallesauto", "/comprar").hasRole("USER")

                .anyRequest().authenticated() 

            )
            .formLogin(form -> form
                .loginPage("/login") 
                .permitAll()
                //.defaultSuccessUrl("/principal", true)
                .successHandler(new LoginRoles())
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
        List<Usuario> clientes = repo_clientes.findAll();

        for (Usuario cliente : clientes) {
            String rawPassword = cliente.getPass(); 
            if (!rawPassword.startsWith("$2a$")) { 
                String encrypted = passwordEncoder().encode(rawPassword);
                //cliente.setPass(rawPassword);(encrypted);
                cliente.setPass(encrypted);
                repo_clientes.save(cliente);
            }
        }
    };
    }



}
