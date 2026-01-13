package com.example.demo.adapters.outbound.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Person;
import com.example.demo.domain.port.PersonRepository;

@Repository
public class JdbcPersonRepository implements PersonRepository {

    private final JdbcTemplate jdbc;
    @NonNull
    private final RowMapper<Person> rowMapper = (rs, rowNum) -> {
        Person p = new Person();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setEmail(rs.getString("email"));
        return p;
    };

    public JdbcPersonRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Person> findAll() {
        return jdbc.query("SELECT id, name, email FROM person", rowMapper);
    }

    @Override
    public Optional<Person> findById(Long id) {
        var list = jdbc.query("SELECT id, name, email FROM person WHERE id = ?", rowMapper, id);
        return list.stream().findFirst();
    }

    @Override
    public Person save(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO person(name, email) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, person.getName());
            ps.setString(2, person.getEmail());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            person.setId(key.longValue());
        }
        return person;
    }

    @Override
    public Person update(Person person) {
        jdbc.update("UPDATE person SET name = ?, email = ? WHERE id = ?", person.getName(), person.getEmail(), person.getId());
        return person;
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM person WHERE id = ?", id);
    }
}
