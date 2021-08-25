package com.rename.me.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type Person. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person implements Serializable {

  private static final long serialVersionUID = -3706717403046249323L;

  @Id
  @Column(nullable = false, unique = true, length = 45)
  private UUID personUUID;

  @Column(nullable = false, length = 45)
  private String firstName;

  @Column(nullable = false, length = 45)
  private String lastName;

  @Column(nullable = false, unique = true, length = 45)
  private String email;

  @Column(nullable = false, length = 45)
  private String dateOfBirth;

  /**
   * Instantiates a new Person.
   *
   * @param firstName the first name
   * @param lastName the last name
   * @param email the email
   * @param dob the dob
   * @throws ParseException the parse exception
   */
  public Person(String firstName, String lastName, String email, String dob) throws ParseException {
    this.personUUID = UUID.randomUUID();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.dateOfBirth = dob;
  }
}
