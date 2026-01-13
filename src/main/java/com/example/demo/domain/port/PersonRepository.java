package com.example.demo.domain.port;

import com.example.demo.domain.Person;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> findAll();

    Optional<Person> findById(Long id);

    Person save(Person person);

    Person update(Person person);

    void deleteById(Long id);
}
