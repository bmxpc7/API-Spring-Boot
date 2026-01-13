package com.example.demo.adapters.inbound.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Person;
import com.example.demo.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/persons")
@Validated
@Tag(name = "Person", description = "Operaciones CRUD sobre personas")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar personas", description = "Obtiene la lista de todas las personas")
    public List<Person> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener persona por id", description = "Devuelve una persona por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Persona encontrada"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public Person get(@Parameter(description = "ID de la persona", required = true) @PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Crear persona", description = "Crea una nueva persona y devuelve la entidad creada con ubicación")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Persona creada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        Person created = service.create(person);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/persons/%d", created.getId())));
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar persona", description = "Actualiza una persona existente por id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Persona actualizada"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public Person update(@Parameter(description = "ID de la persona", required = true) @PathVariable Long id, @Valid @RequestBody Person person) {
        return service.update(id, person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar persona", description = "Elimina una persona por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    public void delete(@Parameter(description = "ID de la persona", required = true) @PathVariable Long id) {
        service.delete(id);
    }
}
