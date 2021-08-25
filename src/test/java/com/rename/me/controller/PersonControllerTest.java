package com.rename.me.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rename.me.exception.InvalidPersonException;
import com.rename.me.exception.PersonAlreadyExistsException;
import com.rename.me.exception.PersonDoesNotExistsException;
import com.rename.me.model.Person;
import com.rename.me.service.PersonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** The type Person controller unit test. */
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

  private PersonController personController;

  @Mock private PersonService personService;

  @Captor private ArgumentCaptor<Person> captorPerson;

  @Captor private ArgumentCaptor<UUID> captorUUID;

  @Captor private ArgumentCaptor<String> captorString;

  private UUID personId;

  private Person person;

  private List<Person> personList;

  /**
   * Sets up.
   *
   * @throws ParseException the parse exception
   */
  @BeforeEach
  void setUp() throws ParseException {
    personId = UUID.randomUUID();

    person = new Person("Amy", "Sanders", "sanders.amy09@gmail.com", "05/24/1986");
    person.setPersonUUID(personId);

    Person person2 = new Person("Adam", "Ali", "ali.adam@gmail.com", "09/04/1934");

    Person person3 = new Person("Sheff", "Bonehead", "bonehead.sheff@gmail.com", "01/21/1995");

    personList = new ArrayList<>();
    personList.add(person);
    personList.add(person2);
    personList.add(person3);

    personController = new PersonController(personService);
  }

  /**
   * Add.
   *
   * @throws PersonAlreadyExistsException the person already exists exception
   * @throws InvalidPersonException
   */
  @Test
  void add() throws PersonAlreadyExistsException, InvalidPersonException {
    given(personService.existsByEmail(person.getEmail())).willReturn(false);
    given(personService.existsById(person.getPersonUUID())).willReturn(false);

    ResponseEntity<String> response = personController.add(person);

    then(personService).should().save(captorPerson.capture());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(person).isEqualTo(captorPerson.getValue());
  }

  /** Register throws person already exists exception. */
  @Test
  void addThrowsPersonAlreadyExistsException() {
    given(personService.existsByEmail(person.getEmail())).willReturn(true);

    assertThrows(
        PersonAlreadyExistsException.class,
        () -> personController.add(person));
  }

  /**
   * Update.
   *
   * @throws PersonDoesNotExistsException the person does not exists exception
   * @throws InvalidPersonException
   */
  @Test
  void update() throws PersonDoesNotExistsException, InvalidPersonException {
    given(personService.existsById(personId)).willReturn(true);

    ResponseEntity<String> response = personController.update(person, personId);

    then(personService).should().update(captorPerson.capture());

    assertThat(captorPerson.getValue()).isEqualTo(person);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  /** Update person throws person already exists exception. */
  @Test
  void updateThrowsPersonDoesNotException() {
    given(personService.existsByEmail(person.getEmail())).willReturn(false);

    assertThrows(
        PersonDoesNotExistsException.class,
        () -> personController.update(person, personId));
  }

  /**
   * Delete.
   *
   * @throws PersonDoesNotExistsException the person does not exists exception
   */
  @Test
  void delete() throws PersonDoesNotExistsException {
    given(personService.existsById(person.getPersonUUID())).willReturn(true);
    given(personService.getById(person.getPersonUUID())).willReturn(person);

    ResponseEntity<String> response = personController.delete(personId);

    then(personService).should().delete(captorPerson.capture());

    assertThat(captorPerson.getValue()).isEqualTo(person);
    assertThat(captorPerson.getValue().getEmail()).isEqualTo(person.getEmail());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  /** Delete person throws person not found exception. */
  @Test
  void deleteThrowsPersonDoesNotExistsException() {
    given(personService.existsById(person.getPersonUUID())).willReturn(false);

    assertThrows(
        PersonDoesNotExistsException.class,
        () -> personController.delete(person.getPersonUUID()));
  }

  /** List all. */
  @Test
  void listAll() {
    given(personService.findAll()).willReturn(personList);
    ResponseEntity<List<Person>> response = personController.listAll();

    then(personService).should().findAll();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(personList);
  }

  /**
   * Gets by email.
   *
   * @throws PersonDoesNotExistsException the person does not exists exception
   */
  @Test
  void getByEmail() throws PersonDoesNotExistsException {
    given(personService.existsByEmail("testMe@gmail.com")).willReturn(true);

    ResponseEntity<Person> response = personController.getByEmail("testMe@gmail.com");

    then(personService).should().getByEmail(captorString.capture());

    assertThat("testMe@gmail.com").isEqualTo(captorString.getValue());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  /** Gets by email throws person not found exception. */
  @Test
  void getByEmailThrowsPersonDoesNotExistsException() {
    given(personService.existsByEmail("testMe@gmail.com")).willReturn(false);

    assertThrows(
        PersonDoesNotExistsException.class,
        () -> personController.getByEmail("testMe@gmail.com"));
  }

  /** Exists by email returns true. */
  @Test
  void existsByEmailReturnsTrue() {
    given(personService.existsByEmail("test.email@gmail.com")).willReturn(true);
    ResponseEntity<Boolean> response = personController.existsByEmail("test.email@gmail.com");

    then(personService).should().existsByEmail(captorString.capture());

    assertThat("test.email@gmail.com").isEqualTo(captorString.getValue());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  /** Exists by email returns false. */
  @Test
  void existsByEmailReturnsFalse() {
    given(personService.existsByEmail("test.email@gmail.com")).willReturn(false);
    ResponseEntity<Boolean> response = personController.existsByEmail("test.email@gmail.com");

    then(personService).should().existsByEmail(captorString.capture());

    assertThat("test.email@gmail.com").isEqualTo(captorString.getValue());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

    /**
   * Gets by email.
   *
   * @throws PersonDoesNotExistsException the person does not exists exception
   */
  @Test
  void getById() throws PersonDoesNotExistsException {
    given(personService.existsById(personId)).willReturn(true);

    ResponseEntity<Person> response = personController.getById(personId);

    then(personService).should().getById(captorUUID.capture());

    assertThat(personId).isEqualTo(captorUUID.getValue());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  /** Gets by email throws person not found exception. */
  @Test
  void getByIdThrowsPersonDoesNotExistsException() {
    given(personService.existsById(personId)).willReturn(false);

    assertThrows(
        PersonDoesNotExistsException.class,
        () -> personController.getById(personId));
  }

  /** Exists by email returns true. */
  @Test
  void existsByIdReturnsTrue() {
    given(personService.existsById(personId)).willReturn(true);
    ResponseEntity<Boolean> response = personController.existsById(personId);

    then(personService).should().existsById(captorUUID.capture());

    assertThat(personId).isEqualTo(captorUUID.getValue());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  /** Exists by email returns false. */
  @Test
  void existsByIdReturnsFalse() {
    given(personService.existsById(personId)).willReturn(false);
    ResponseEntity<Boolean> response = personController.existsById(personId);

    then(personService).should().existsById(captorUUID.capture());

    assertThat(personId).isEqualTo(captorUUID.getValue());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
