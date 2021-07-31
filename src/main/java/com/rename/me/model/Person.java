package com.rename.me.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Person.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person implements Serializable {
	
	@Id
	private UUID personUUID;

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

	private Date dateOfBirth;

	/**
	 * Instantiates a new Person.
	 *
	 * @param firstName the first name
	 * @param lastName  the last name
	 * @param email     the email
	 * @param dob       the dob
	 * @throws ParseException the parse exception
	 */
	public Person(String firstName, String lastName, String email, String dob) throws ParseException {
		this.personUUID = UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		setDateOfBirth(dob);
	}

	/**
	 * Sets date of birth.
	 *
	 * @param dob the dob
	 * @throws ParseException the parse exception
	 */
	public void setDateOfBirth(String dob) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse(dob);
		this.dateOfBirth = date;
	}
}
