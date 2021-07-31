package com.rename.me.controller;

import java.util.List;

import com.rename.me.exception.PersonAlreadyExistsException;
import com.rename.me.exception.PersonDoesNotExistsException;
import com.rename.me.model.Person;
import com.rename.me.service.PersonService;

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
   * @throws PersonDoesNotExistsException the person does not exists exception
   */
  @PostMapping(
      value = "/add",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> add(@RequestBody Person person)
      throws PersonAlreadyExistsException, PersonDoesNotExistsException {

		if (personService.existsByEmail(person.getEmail()))
            throw new PersonAlreadyExistsException();

        personService.save(person);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("PersonController", "add");
        return new ResponseEntity<>("Successfully Created Person", responseHeaders, HttpStatus.CREATED);
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
            } else
                throw new PersonDoesNotExistsException();
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
            Person _customer = personService.getByEmail(email);
            personService.delete(_customer);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("PersonController", "delete");
            return new ResponseEntity<>("Successfully Deleted Person", responseHeaders, HttpStatus.OK);
        } else
            throw new PersonDoesNotExistsException();
    }

  /**
   * List all response entity.
   *
   * @return the response entity
   */
  @GetMapping(value = "/listAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Person>> listAll() {
        // @RequestParam(defaultValue = "email") String sortBy
        List<Person> list = personService.findAll(); // sortBy

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
  @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Person> getByEmail(@PathVariable String email)
      throws PersonDoesNotExistsException {
            if (personService.existsByEmail(email)) {
                Person _customer = personService.getByEmail(email);

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("PersonController", "getByEmail");
                return new ResponseEntity<>(_customer, responseHeaders, HttpStatus.OK);
            } else
                throw new PersonDoesNotExistsException();
		}
}
