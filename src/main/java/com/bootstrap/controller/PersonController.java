package com.bootstrap.me.controller;

import java.util.List;
import java.util.UUID;

import com.bootstrap.me.exception.InvalidPersonException;
import com.bootstrap.me.exception.PersonAlreadyExistsException;
import com.bootstrap.me.exception.PersonDoesNotExistsException;
import com.bootstrap.me.interfaces.ControllerI;
import com.bootstrap.me.model.Person;
import com.bootstrap.me.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Person controller. */
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

  /**
   * Add response entity.
   *
   * @param person the person
   * @return the response entity
   * @throws PersonAlreadyExistsException the person already exists exception
   * @throws PersonDoesNotExistsException
   */
  @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> add(@RequestBody Person person) throws PersonAlreadyExistsException, InvalidPersonException {
    if (!person.equals(null)) {
      if (!personService.existsByEmail(person.getEmail())) {
        if(!personService.existsById(person.getPersonUUID())) {
          personService.save(person);

          HttpHeaders responseHeaders = new HttpHeaders();
          responseHeaders.set("PersonController", "add");
          return new ResponseEntity<>("Successfully Created Person", responseHeaders, HttpStatus.CREATED);
        } else throw new PersonAlreadyExistsException();
      } else throw new PersonAlreadyExistsException();
    } else throw new InvalidPersonException();
  }

  /**
   * Update person.
   *
   * @param person the person
   * @return the response entity
   * @throws PersonDoesNotExistsException the person not found exception
   * @throws InvalidPersonException
   */
  @PutMapping(value = "/update/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> update(@RequestBody Person person, @PathVariable UUID personId)
      throws PersonDoesNotExistsException, InvalidPersonException {
        if (person.equals(null)) throw new InvalidPersonException();
    if (personService.existsByEmail(person.getEmail()) || personService.existsById(personId)) {
      person.setPersonUUID(personId);
      personService.update(person);

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("PersonController", "update");
      return new ResponseEntity<>("Successfully Updated Person", responseHeaders, HttpStatus.OK);
    } else throw new PersonDoesNotExistsException();
  }

  /**
   * Delete person.
   *
   * @param email the email
   * @return the response entity
   * @throws PersonDoesNotExistsException the person not found exception
   */
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

  /**
   * List all response entity.
   *
   * @return the response entity
   */
  @GetMapping(value = "/listAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Person>> listAll() {
    List<Person> list = personService.findAll();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("PersonController", "list");
    return new ResponseEntity<>(list, responseHeaders, HttpStatus.OK);
  }

  /**
   * Gets by email.
   *
   * @param email the email
   * @return the by email
   * @throws PersonDoesNotExistsException the person not found exception
   */
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

  /**
   * Exists by email.
   *
   * @param email the email
   * @return the by email
   */
  @GetMapping(value = "/existsByEmail/{email}")
  public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("PersonController", "existsByEmail");

    if (personService.existsByEmail(email)) {
      return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
    } else return new ResponseEntity<>(false, responseHeaders, HttpStatus.OK);
  }

    /**
   * Gets by email.
   *
   * @param email the email
   * @return the by email
   * @throws PersonDoesNotExistsException the person not found exception
   */
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

  /**
   * Exists by email.
   *
   * @param email the email
   * @return the by email
   */
  @GetMapping(value = "/existsById/{personId}")
  public ResponseEntity<Boolean> existsById(@PathVariable UUID personId) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("PersonController", "existsById");

    if (personService.existsById(personId)) {
      return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
    } else return new ResponseEntity<>(false, responseHeaders, HttpStatus.OK);
  }
}
