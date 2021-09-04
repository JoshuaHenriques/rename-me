package com.bootstrap.service;

import com.bootstrap.interfaces.ServiceI;
import com.bootstrap.model.Person;
import com.bootstrap.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Person service.
 */
@Service
public class PersonService implements ServiceI {

    private final PersonRepository personRepository;

    /**
     * Instantiates a new Person service.
     *
     * @param personRepository the person repository
     */
    @Autowired
    public PersonService(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public void save(Person person) {

        personRepository.save(person);
    }

    public void delete(Person person) {

        personRepository.delete(person);
    }

    /**
     * Update.
     *
     * @param person the person
     */
    public void update(Person person) {

        personRepository.save(person);
    }

    public List<Person> findAll() {

        return personRepository.findAll();
    }

    public boolean existsByEmail(String email) {

        return personRepository.existsByEmail(email);
    }

    public Person getByEmail(String email) {

        return personRepository.getByEmail(email);
    }

    public boolean existsById(UUID uuid) {

        return personRepository.existsById(uuid);
    }

    public Person getById(UUID uuid) {

        return personRepository.getById(uuid);
    }
}
