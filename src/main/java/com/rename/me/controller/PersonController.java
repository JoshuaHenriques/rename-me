package com.rename.me.controller;

import com.rename.me.exception.PersonAlreadyExistsException;
import com.rename.me.exception.PersonDoesNotExistsException;
import com.rename.me.model.Person;
import com.rename.me.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** The type Person controller. */
@RestController
@RequestMapping("api/person")
public class PersonController {

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
   */
  @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> add(@RequestBody Person person)
      throws PersonAlreadyExistsException {

    if (personService.existsByEmail(person.getEmail())) {throw new PersonAlreadyExistsException();}
    else {
      personService.save(person);

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("PersonController", "add");
      return new ResponseEntity<>("Successfully Created Person", responseHeaders, HttpStatus.CREATED);
    }
  }

  /**
   * Update person.
   *
   * @param person the person
   * @return the response entity
   * @throws PersonDoesNotExistsException the person not found exception
   */
  @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> update(@RequestBody Person person)
      throws PersonDoesNotExistsException {
    if (personService.existsByEmail(person.getEmail())) {
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
  @DeleteMapping(value = "/delete/{email}")
  public ResponseEntity<String> delete(@PathVariable String email)
      throws PersonDoesNotExistsException {
    if (personService.existsByEmail(email)) {
      Person _person = personService.getByEmail(email);
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
  @GetMapping(value = "/get/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
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
  @GetMapping(value = "/{email}/exists")
  public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("PersonController", "existsByEmail");

    if (personService.existsByEmail(email)) {
      return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
    } else return new ResponseEntity<>(false, responseHeaders, HttpStatus.OK);
  }
}
