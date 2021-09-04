package com.bootstrap.interfaces;

import com.bootstrap.model.Person;

import java.util.List;
import java.util.UUID;

/**
 * The interface Service i.
 */
public interface ServiceI {

    /**
     * Save.
     *
     * @param person the person
     */
    void save(Person person);

    /**
     * Delete.
     *
     * @param person the person
     */
    void delete(Person person);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Person> findAll();

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);

    /**
     * Gets by email.
     *
     * @param email the email
     * @return the by email
     */
    Person getByEmail(String email);

    /**
     * Exists by id boolean.
     *
     * @param uuid the uuid
     * @return the boolean
     */
    boolean existsById(UUID uuid);

    /**
     * Gets by id.
     *
     * @param uuid the uuid
     * @return the by id
     */
    Person getById(UUID uuid);
}
