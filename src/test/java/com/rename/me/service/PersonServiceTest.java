package com.rename.me.service;

import com.rename.me.model.Person;
import com.rename.me.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/** The type Person service unit test. */
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

  private PersonService personService;

  @Mock private PersonRepository personRepository;

  @Captor private ArgumentCaptor<Person> captorPerson;

  @Captor private ArgumentCaptor<String> captorString;

  private Person person;

  private List<Person> personList;

  @BeforeEach
  void setUp() throws ParseException {
    person = new Person("Amy", "Sanders", "sanders.amy09@gmail.com", "05/24/1986");

    Person person2 = new Person("Adam", "Ali", "ali.adam@gmail.com", "09/04/1934");

    Person person3 = new Person("Sheff", "Bonehead", "bonehead.sheff@gmail.com", "01/21/1995");

    personList = new ArrayList<>();
    personList.add(person);
    personList.add(person2);
    personList.add(person3);

    personService = new PersonService(personRepository);
  }

  @Test
  void save() {
    personService.save(this.person);

    then(personRepository).should().save(captorPerson.capture());

    assertThat(captorPerson.getValue()).isEqualTo(this.person);
  }

  /** Delete. */
  @Test
  void delete() {
    personService.delete(this.person);

    then(personRepository).should().delete(captorPerson.capture());

    assertThat(captorPerson.getValue()).isEqualTo(this.person);
  }

  /** Update. */
  @Test
  void update() {
    personService.update(this.person);

    then(personRepository).should().save(captorPerson.capture());

    assertThat(captorPerson.getValue()).isEqualTo(this.person);
  }

  /** Find all customers. Do later. */
  @Test
  void findAllCustomers() {
    given(personRepository.findAll()).willReturn(personList);
    List<Person> _personList = personService.findAll();

    then(personRepository).should().findAll();

    assertThat(personList).isEqualTo(_personList);
  }

  @Test
  void existsByEmailWillReturnTrue() {
    given(personRepository.existsByEmail("test.email@gmail.com")).willReturn(true);
    boolean exists = personService.existsByEmail("test.email@gmail.com");

    then(personRepository).should().existsByEmail(captorString.capture());

    assertThat("test.email@gmail.com").isEqualTo(captorString.getValue());
    assertThat(exists).isTrue();
  }

  @Test
  void existsByEmailWillReturnFalse() {
    given(personRepository.existsByEmail("test.email@gmail.com")).willReturn(false);
    boolean exists = personService.existsByEmail("test.email@gmail.com");

    then(personRepository).should().existsByEmail(captorString.capture());

    assertThat("test.email@gmail.com").isEqualTo(captorString.getValue());
    assertThat(exists).isFalse();
  }

  @Test
  void getByEmail() {
    String email = "testMe@gmail.com";
    given(personRepository.getByEmail(email)).willReturn(person);
    Person _person = personService.getByEmail(email);

    then(personRepository).should().getByEmail(captorString.capture());

    assertThat(captorString.getValue()).isEqualTo(email);
    assertThat(person).isEqualTo(_person);
  }
}
