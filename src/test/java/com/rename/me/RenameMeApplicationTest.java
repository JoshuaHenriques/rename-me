// package com.rename.me;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.text.ParseException;
// import java.util.UUID;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.rename.me.model.Person;
// import com.rename.me.repository.PersonRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;

// /** The type Rename me application tests. */
// @SpringBootTest
// @ExtendWith(SpringExtension.class)
// @AutoConfigureMockMvc
// class RenameMeApplicationTest {

//   @Autowired private MockMvc mockMvc;

//   @Autowired private PersonRepository personRepository;

//   private Person person0, person1, person2;

//   private UUID person0Id;

//   /**
//    * As json string string.
//    *
//    * @param obj the obj
//    * @return the string
//    */
//   public static String asJsonString(final Object obj) {
//     try {
//       final ObjectMapper mapper = new ObjectMapper();
//       return mapper.writeValueAsString(obj);
//     } catch (Exception e) {
//       throw new RuntimeException(e);
//     }
//   }

//   /**
//    * Sets up.
//    *
//    * @throws ParseException the parse exception
//    */
//   @BeforeEach
//   void setUp() throws ParseException {
//     person0Id = UUID.randomUUID();
//     person0 = new Person("Jay", "Williams", "william.jay@gmail.com", "02/04/1985");
//     person0.setPersonUUID(person0Id);
//     person1 = new Person("Adam", "Ali", "ali.adam@gmail.com", "09/04/1934");
//     person2 = new Person("Sheff", "Bonehead", "bonehead.sheff@gmail.com", "01/21/1995");
//   }

//   /**
//    * Register.
//    *
//    * @throws Exception the exception
//    */
//   @Test
//   void renameMeApplicationTest() throws Exception {

//     mockMvc
//         .perform(
//             post("/api/person/add")
//                 .contentType(MediaType.APPLICATION_JSON_VALUE)
//                 .content(asJsonString(person0)))
//         .andExpect(status().isCreated());

//     mockMvc
//         .perform(
//             post("/api/person/add")
//                 .contentType(MediaType.APPLICATION_JSON_VALUE)
//                 .content(asJsonString(person1)))
//         .andExpect(status().isCreated());

//     mockMvc
//         .perform(
//             post("/api/person/add")
//                 .contentType(MediaType.APPLICATION_JSON_VALUE)
//                 .content(asJsonString(person2)))
//         .andExpect(status().isCreated());

//     Person addPerson = personRepository.getByEmail("william.jay@gmail.com");
//     assertThat(addPerson.getEmail()).isEqualTo("william.jay@gmail.com");

//     person0.setFirstName("JayJay");

//     mockMvc
//         .perform(
//             put("/api/person/update/{person0Id}", person0Id)
//                 .contentType(MediaType.APPLICATION_JSON_VALUE)
//                 .content(asJsonString(person0)))
//         .andExpect(status().isOk());

//     Person updatePerson = personRepository.getByEmail("william.jay@gmail.com");
//     assertThat(updatePerson.getFirstName()).isEqualTo("JayJay");

//     // List<Person> personList = personRepository.findAll();
//     // assertThat(personList.size()).isEqualTo(3);

//     // assertThat(personRepository.existsByEmail("bonehead.sheff@gmail.com")).isTrue();

//     // mockMvc.perform(delete("/api/person/delete/{person0Id}", person0Id)).andExpect(status().isOk());

//     // personList = personRepository.findAll();
//     // assertThat(personList.size()).isEqualTo(2);
//   }
// }
