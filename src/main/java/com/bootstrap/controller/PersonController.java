package com.bootstrap.controller;

import com.bootstrap.exception.InvalidPersonException;
import com.bootstrap.exception.PersonAlreadyExistsException;
import com.bootstrap.exception.PersonDoesNotExistsException;
import com.bootstrap.interfaces.ControllerI;
import com.bootstrap.model.Person;
import com.bootstrap.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The type Person controller.
 */
@RestController
@RequestMapping("api/person")
public class PersonController implements ControllerI {

    private final PersonService personService;

    /**
     * Instantiates a new Person controller.
     *
     * @param personService the person service
     */
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Person person) throws PersonAlreadyExistsException, InvalidPersonException {
        if (person != null) {
            if (!personService.existsByEmail(person.getEmail())) {
                if (!personService.existsById(person.getPersonUUID())) {
                    personService.save(person);

                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.set("PersonController", "add");
                    return new ResponseEntity<>("Successfully Created Person", responseHeaders, HttpStatus.CREATED);
                } else throw new PersonAlreadyExistsException();
            } else throw new PersonAlreadyExistsException();
        } else throw new InvalidPersonException();
    }

    @PutMapping(value = "/update/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Person person, @PathVariable UUID personId)
            throws PersonDoesNotExistsException, InvalidPersonException {
        if (person == null) throw new InvalidPersonException();
        if (personService.existsByEmail(person.getEmail()) || personService.existsById(personId)) {
            person.setPersonUUID(personId);
            personService.update(person);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("PersonController", "update");
            return new ResponseEntity<>("Successfully Updated Person", responseHeaders, HttpStatus.OK);
        } else throw new PersonDoesNotExistsException();
    }

    @DeleteMapping(value = "/delete/{personId}")
    public ResponseEntity<String> delete(@PathVariable UUID personId)
            throws PersonDoesNotExistsException {
        if (personService.existsById(personId)) {
            Person _person = personService.getById(personId);
            personService.delete(_person);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("PersonController", "delete");
            return new ResponseEntity<>("Successfully Deleted Person", responseHeaders, HttpStatus.OK);
        } else throw new PersonDoesNotExistsException();
    }

    @GetMapping(value = "/listAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> listAll() {
        List<Person> list = personService.findAll();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("PersonController", "list");
        return new ResponseEntity<>(list, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/getByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getByEmail(@PathVariable String email)
            throws PersonDoesNotExistsException {
        if (personService.existsByEmail(email)) {
            Person _person = personService.getByEmail(email);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("PersonController", "getByEmail");
            return new ResponseEntity<>(_person, responseHeaders, HttpStatus.OK);
        } else throw new PersonDoesNotExistsException();
    }

    @GetMapping(value = "/existsByEmail/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("PersonController", "existsByEmail");

        if (personService.existsByEmail(email)) {
            return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
        } else return new ResponseEntity<>(false, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/getId/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getById(@PathVariable UUID personId)
            throws PersonDoesNotExistsException {
        if (personService.existsById(personId)) {
            Person _person = personService.getById(personId);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("PersonController", "getBId");
            return new ResponseEntity<>(_person, responseHeaders, HttpStatus.OK);
        } else throw new PersonDoesNotExistsException();
    }

    @GetMapping(value = "/existsById/{personId}")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID personId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("PersonController", "existsById");

        if (personService.existsById(personId)) {
            return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
        } else return new ResponseEntity<>(false, responseHeaders, HttpStatus.OK);
    }
}
