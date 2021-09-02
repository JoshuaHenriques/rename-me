package com.rename.me.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.util.UUID;

import com.bootstrap.me.controller.PersonController;
import com.bootstrap.me.model.Person;
import com.bootstrap.me.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** The type Person controller web mvc test. */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerWebMvcTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PersonService personService;

  private Person person0;

  private UUID person0Id;

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
    person0Id = UUID.randomUUID();
    person0 = new Person("Jay", "Williams", "william.jay@gmail.com", "02/04/1985");
    person0.setPersonUUID(person0Id);
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void add() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(false);
    given(personService.existsById(person0.getPersonUUID())).willReturn(false);

    mockMvc
        .perform(
            post("/api/person/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    given(personService.existsById(person0.getPersonUUID())).willReturn(true);

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
    given(personService.existsById(person0.getPersonUUID())).willReturn(true);

    mockMvc
        .perform(
            put("/api/person/update/{person0Id}", person0Id)
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
    given(personService.existsById(person0.getPersonUUID())).willReturn(false);


    mockMvc
        .perform(
            put("/api/person/update/{person0Id}", person0Id)
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
    given(personService.existsById(person0.getPersonUUID())).willReturn(true);


    mockMvc.perform(delete("/api/person/delete/{person0Id}", person0Id)).andExpect(status().isOk());
  }

  /**
   * Register throws customer already exists exception.
   *
   * @throws Exception the exception
   */
  @Test
  void deleteThrowsPersonDoesNotExistsException() throws Exception {
    given(personService.existsById(person0.getPersonUUID())).willReturn(false);

    mockMvc
        .perform(delete("/api/person/delete/{person0Id}", person0Id))
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

    mockMvc.perform(get("/api/person/getByEmail/william.jay@gmail.com")).andExpect(status().isOk());
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
        .perform(get("/api/person/getByEmail/william.jay@gmail.com"))
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

    mockMvc.perform(get("/api/person/existsByEmail/william.jay@gmail.com")).andExpect(status().isOk());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void existsByEmailReturnsFalse() throws Exception {
    given(personService.existsByEmail(person0.getEmail())).willReturn(false);

    mockMvc.perform(get("/api/person/existsByEmail/william.jay@gmail.com")).andExpect(status().isOk());
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

    /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void getById() throws Exception {
    given(personService.existsById(person0Id)).willReturn(true);

    mockMvc.perform(get("/api/person/getId/{person0Id}", person0Id)).andExpect(status().isOk());
  }

  /**
   * Register throws customer already exists exception.
   *
   * @throws Exception the exception
   */
  @Test
  void getByIdThrowsPersonDoesNotExistsException() throws Exception {
    given(personService.existsById(person0Id)).willReturn(false);

    mockMvc
        .perform(get("/api/person/getId/{person0Id}", person0Id))
        .andExpect(status().isBadRequest());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void existsByIdReturnsTrue() throws Exception {
    given(personService.existsById(person0.getPersonUUID())).willReturn(true);

    mockMvc.perform(get("/api/person/existsById/{person0Id}", person0Id)).andExpect(status().isOk());
  }

  /**
   * Register.
   *
   * @throws Exception the exception
   */
  @Test
  void existsByIdReturnsFalse() throws Exception {
    given(personService.existsById(person0.getPersonUUID())).willReturn(false);

    mockMvc.perform(get("/api/person/existsById/{person0Id}", person0Id)).andExpect(status().isOk());
  }
}
