package com.rename.me.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/** The type Person. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person implements Serializable {

  @Id private UUID personUUID;

  private String firstName;
  private String lastName;
  private String email;

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
