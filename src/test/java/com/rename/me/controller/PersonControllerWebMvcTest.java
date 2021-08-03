package com.rename.me.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rename.me.model.Person;
import com.rename.me.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The type Person controller web mvc test. */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerWebMvcTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PersonService personService;

  private Person person0;

  /**
   * As json string string.
   *
   * @param obj the obj
   * @return the string
   */
  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sets up.
   *
   * @throws ParseException the parse exception
   */
  @BeforeEach
  void setUp() throws ParseException {
    person0 = new Person("Jay", "Williams", "william.jay@gmail.com", "02/04/1985");
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void add() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(false);

    mockMvc
        .perform(
            post("/api/person/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(person0)))
        .andExpect(status().isCreated());
  }

  /**
   * Register throws customer already exists exception.
   *
   * @throws Exception the exception
   */
  @Test
  void addThrowsPersonAlreadyExistsException() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(true);

    mockMvc
        .perform(
            post("/api/person/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(person0)))
        .andExpect(status().isBadRequest());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void update() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(true);

    mockMvc
        .perform(
            put("/api/person/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(person0)))
        .andExpect(status().isOk());
  }

  /**
   * Register throws customer already exists exception.
   *
   * @throws Exception the exception
   */
  @Test
  void updateThrowsPersonDoesNotExistsException() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(false);

    mockMvc
        .perform(
            put("/api/person/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(person0)))
        .andExpect(status().isBadRequest());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void deletePerson() throws Exception {
    given(personService.existsByEmail("william.jay@gmail.com")).willReturn(true);

    mockMvc.perform(delete("/api/person/delete/william.jay@gmail.com")).andExpect(status().isOk());
  }

  /**
   * Register throws customer already exists exception.
   *
   * @throws Exception the exception
   */
  @Test
  void deleteThrowsPersonDoesNotExistsException() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(false);

    mockMvc
        .perform(delete("/api/person/delete/william.jay@gmail.com"))
        .andExpect(status().isBadRequest());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void getByEmail() throws Exception {
    given(personService.existsByEmail("william.jay@gmail.com")).willReturn(true);

    mockMvc.perform(get("/api/person/get/william.jay@gmail.com")).andExpect(status().isOk());
  }

  /**
   * Register throws customer already exists exception.
   *
   * @throws Exception the exception
   */
  @Test
  void getByEmailThrowsPersonDoesNotExistsException() throws Exception {
    given(personService.existsByEmail("william.jay@gmail.com")).willReturn(false);

    mockMvc
        .perform(get("/api/person/get/william.jay@gmail.com"))
        .andExpect(status().isBadRequest());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void existsByEmailReturnsTrue() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(true);

    mockMvc.perform(get("/api/person/william.jay@gmail.com/exists")).andExpect(status().isOk());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void existsByEmailReturnsFalse() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(false);

    mockMvc.perform(get("/api/person/william.jay@gmail.com/exists")).andExpect(status().isOk());
  }

  /**
   * List all persons.
   *
   * @throws Exception the exception
   */
  @Test
  void listAllPersons() throws Exception {

    mockMvc.perform(get("/api/person/listAll")).andExpect(status().isOk());
  }
}
