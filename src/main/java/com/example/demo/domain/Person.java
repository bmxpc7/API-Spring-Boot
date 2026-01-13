package com.example.demo.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de dominio que representa a una persona")
public class Person {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @Schema(description = "Nombre completo de la persona", example = "Juan Pérez")
    private String name;

    @Schema(description = "Correo electrónico", example = "juan.perez@example.com")
    private String email;

    public Person() {}

    public Person(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
