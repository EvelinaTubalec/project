package com.leverx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.config.ApplicationConfig;
import com.leverx.model.dto.request.EmployeeRequestDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@WebAppConfiguration
class EmployeeControllerTest {

  public MockMvc mockMvc;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void findAll() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(status().isOk());
  }

  @Test
  void saveEmployee() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/users")
                .content(
                    asJsonString(
                        new EmployeeRequestDto("firstName", "LastName", "email", "password", "position", 3L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void updateEmployee() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/users/{id}", 2L)
                .content(
                    asJsonString(
                        new EmployeeRequestDto("firstName", "LastName", "email", "password", "position", 3L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void deleteEmployee() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/users/{id}", 2L))
        .andExpect(status().isNoContent());
  }

  @SneakyThrows
  public static String asJsonString(final Object obj) {
    return new ObjectMapper().writeValueAsString(obj);
  }
}
