package com.automundo.concesionaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ConcesionariaApplication {

    private static final Logger logger = LoggerFactory.getLogger(ConcesionariaApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando la aplicación Concesionaria...");
        SpringApplication.run(ConcesionariaApplication.class, args);
        logger.info("Aplicación iniciada correctamente.");
    }
}
