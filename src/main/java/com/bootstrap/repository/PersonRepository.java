package com.bootstrap.me.repository;

import java.util.UUID;

import com.bootstrap.me.model.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** The interface Person repository. */
@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
  /**
   * Exists by email boolean.
   *
   * @param email the email
   * @return the boolean
   */
  @Query(
      "select case when count(p)> 0 then true else false end from Person p where lower(p.email) like lower(:email)")
  boolean existsByEmail(@Param("email") String email);

  /**
   * Gets by email.
   *
   * @param email the email
   * @return the by email
   */
  @Query(value = "SELECT * FROM person WHERE person.email=:email", nativeQuery = true)
  Person getByEmail(@Param("email") String email);
}
