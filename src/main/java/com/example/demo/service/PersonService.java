package com.example.demo.service;

import com.example.demo.domain.Person;
import com.example.demo.domain.port.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public Person getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public Person create(Person person) {
        return repository.save(person);
    }

    public Person update(Long id, Person person) {
        if (repository.findById(id).isEmpty()) {
            throw new RuntimeException("Person not found");
        }
        person.setId(id);
        return repository.update(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
