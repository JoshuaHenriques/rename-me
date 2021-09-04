package com.bootstrap.interfaces;

import com.bootstrap.exception.InvalidPersonException;
import com.bootstrap.exception.PersonAlreadyExistsException;
import com.bootstrap.exception.PersonDoesNotExistsException;
import com.bootstrap.model.Person;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The interface Controller i.
 */
public interface ControllerI {

    /**
     * Add response entity.
     *
     * @param person the person
     * @return the response entity
     * @throws PersonAlreadyExistsException the person already exists exception
     * @throws InvalidPersonException       the invalid person exception
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> add(@RequestBody Person person) throws PersonAlreadyExistsException, InvalidPersonException;

    /**
     * Update response entity.
     *
     * @param person   the person
     * @param personId the person id
     * @return the response entity
     * @throws PersonDoesNotExistsException the person does not exists exception
     * @throws InvalidPersonException       the invalid person exception
     */
    @PutMapping(value = "/update/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> update(@RequestBody @ModelAttribute Person person, @PathVariable UUID personId)
            throws PersonDoesNotExistsException, InvalidPersonException;

    /**
     * Delete response entity.
     *
     * @param personId the person id
     * @return the response entity
     * @throws PersonDoesNotExistsException the person does not exists exception
     */
    @DeleteMapping(value = "/delete/{personId}")
    ResponseEntity<String> delete(@PathVariable UUID personId) throws PersonDoesNotExistsException;

    /**
     * List all response entity.
     *
     * @return the response entity
     */
    @GetMapping(value = "/listAll", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Person>> listAll();

    /**
     * Gets by email.
     *
     * @param email the email
     * @return the by email
     * @throws PersonDoesNotExistsException the person does not exists exception
     */
    @GetMapping(value = "/get/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Person> getByEmail(@PathVariable String email) throws PersonDoesNotExistsException;

    /**
     * Exists by email response entity.
     *
     * @param email the email
     * @return the response entity
     */
    @GetMapping(value = "/{email}/exists")
    ResponseEntity<Boolean> existsByEmail(@PathVariable String email);

    /**
     * Gets by id.
     *
     * @param personId the person id
     * @return the by id
     * @throws PersonDoesNotExistsException the person does not exists exception
     */
    @GetMapping(value = "/get/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Person> getById(@PathVariable UUID personId) throws PersonDoesNotExistsException;

    /**
     * Exists by id response entity.
     *
     * @param personId the person id
     * @return the response entity
     */
    @GetMapping(value = "/{personId}/exists")
    ResponseEntity<Boolean> existsById(@PathVariable UUID personId);
}
