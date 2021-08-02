package com.rename.me.controller;

import com.rename.me.exception.PersonAlreadyExistsException;
import com.rename.me.exception.PersonDoesNotExistsException;
import com.rename.me.model.Person;
import com.rename.me.service.PersonService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/** The type Person controller unit test. */
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

  private PersonController personController;

  @Mock private PersonService personService;

  @Captor private ArgumentCaptor<Person> captorPerson;

  @Captor private ArgumentCaptor<String> captorString;

  private Person person, person2, person3;

  private List<Person> personList;

  @BeforeEach
  void setUp() throws ParseException {
    person = new Person("Amy", "Sanders", "sanders.amy09@gmail.com", "05/24/1986");

    person2 = new Person("Adam", "Ali", "ali.adam@gmail.com", "09/04/1934");

    person3 = new Person("Sheff", "Bonehead", "bonehead.sheff@gmail.com", "01/21/1995");

    personList = new ArrayList<>();
    personList.add(person);
    personList.add(person2);
    personList.add(person3);

    personController = new PersonController(personService);
  }

  @Test
  void add() throws PersonAlreadyExistsException {
    given(personService.existsByEmail(person.getEmail())).willReturn(false);

    ResponseEntity<String> response = personController.add(person);

    then(personService).should().save(captorPerson.capture());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(person).isEqualTo(captorPerson.getValue());
  }

  /**
   * Register throws customer already exists exception.
   */
  @Test
  void addThrowsPersonAlreadyExistsException() {
    given(personService.existsByEmail(person.getEmail())).willReturn(true);

    assertThrows(PersonAlreadyExistsException.class, () -> {
      personController.add(person);
    });
  }

  @Test
  void update() throws PersonDoesNotExistsException {
    given(personService.existsByEmail(person.getEmail())).willReturn(true);

    ResponseEntity<String> response = personController.update(person);

    then(personService).should(). update(captorPerson.capture());

    assertThat(captorPerson.getValue()).isEqualTo(person);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  /**
   * Update customer throws customer already exists exception.
   */
  @Test
  void updateThrowsPersonDoesNotException() {
    given(personService.existsByEmail(person.getEmail())).willReturn(false);

    assertThrows(PersonDoesNotExistsException.class, () -> {
      personController.update(person);
    });
  }

  @Test
  void delete() {}

  @Test
  void listAll() {}

  @Test
  void getByEmail() {}

  @Test
  void existsByEmail() {}
}
