package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo API - Personas")
                        .version("v1.0.0")
                        .description("API de ejemplo (arquitectura hexagonal) para gestionar personas usando SQLite")
                        .contact(new Contact().name("Equipo Demo").email("dev@example.com"))
                );
    }
}
