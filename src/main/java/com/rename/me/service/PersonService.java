package com.rename.me.service;

import java.util.List;

import com.rename.me.model.Person;
import com.rename.me.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Person service.
 */
@Service
public class PersonService {
	
	private final PersonRepository personRepository;

    /**
     * Instantiates a new Person service.
     *
     * @param personRepository the person repository
     */
@Autowired
    public PersonService(PersonRepository personRepository) {

        this.personRepository = personRepository;
        // this.orderDBService = orderDBService;
    }

	/**
     * Exists by phone number boolean.
     *
     * @param email the email
     * @return the boolean
     */
public boolean existsByEmail(String email) {

        return personRepository.existsByEmail(email);
    }

	    /**
         * Gets by email.
         *
         * @param email the email
         * @return the by email
         */
public Person getByEmail(String email) {

        return personRepository.getByEmail(email);
    }

    /**
     * Save.
     *
     * @param person the person
     */
public void save(Person person) {

        personRepository.save(person);
    }

    /**
     * Delete.
     *
     * @param person the person
     */
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

    /**
     * Find all list.
     *
     * @return the list
     */
public List<Person> findAll() {
        // String sortBy
        return personRepository.findAll();
    }
}
