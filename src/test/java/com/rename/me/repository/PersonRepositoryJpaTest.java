package com.rename.me.repository;

import java.text.ParseException;

import com.rename.me.model.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * The type Person repository jpa test.
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class PersonRepositoryJpaTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PersonRepository personRepository;

	private Person person;

	/**
     * Sets up.
     *
     * @throws ParseException the parse exception
     */
@BeforeEach
	void setUp() throws ParseException {
		person = new Person("Jay", "Williams", "william.jjj@gmail.com", "02/25/1985");
	}
}
