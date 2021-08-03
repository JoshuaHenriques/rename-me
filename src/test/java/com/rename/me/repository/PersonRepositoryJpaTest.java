package com.rename.me.repository;

import com.rename.me.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** The type Person repository jpa test. */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PersonRepositoryJpaTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private PersonRepository personRepository;

  private Person person0, person1, person2;

  /**
   * Sets up.
   *
   * @throws ParseException the parse exception
   */
  @BeforeEach
  void setUp() throws ParseException {
    person0 = new Person("Jay", "Williams", "william.jay@gmail.com", "02/25/1985");
    person1 = new Person("Braxton", "Redis", "redis.braxton@gmail.com", "02/25/1985");
    person2 = new Person("Chris", "Knoxville", "knoxvillw.chris@gmail.com", "02/25/1985");
  }

  /** Database should be empty. */
  @Test
  void emptyDatabase() {
    List<Person> personList = personRepository.findAll();
    assertThat(personList).isEmpty();
  }

  /** Database should store customer. */
  @Test
  void storePerson() {
    Person _person = personRepository.save(person0);

    assertThat(_person).hasFieldOrPropertyWithValue("firstName", "Jay");
    assertThat(_person).hasFieldOrPropertyWithValue("lastName", "Williams");
    assertThat(_person).hasFieldOrPropertyWithValue("email", "william.jay@gmail.com");
  }

  /** Database should store customer. */
  @Test
  void deletePerson() {
    Person _person = entityManager.persist(person0);

    personRepository.delete(_person);
    // entityManager.flush();

    Person __person = entityManager.find(Person.class, _person.getPersonUUID());
    assertThat(__person).isNull();
  }

  /** Find all inventory. */
  @Test
  void findAllInventory() {
    entityManager.persist(person0);
    entityManager.persist(person1);
    entityManager.persist(person2);

    List<Person> customer = personRepository.findAll();

    assertThat(customer).hasSize(3).contains(person0, person1, person2);
  }

  /** Exists by email. */
  @Test
  void existsByEmail() {
    entityManager.persist(person0);
    entityManager.persist(person1);

    boolean exists = personRepository.existsByEmail("redis.braxton@gmail.com");

    assertThat(exists).isTrue();
  }

  /** Gets by email. */
  @Test
  void getByEmail() {
    entityManager.persist(person0);
    entityManager.persist(person1);

    Person customer = personRepository.getByEmail("redis.braxton@gmail.com");

    assertThat(customer).isEqualTo(person1);
  }
}
