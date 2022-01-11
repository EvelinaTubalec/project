package com.leverx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.config.ApplicationConfig;
import com.leverx.model.dto.requests.DepartmentRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@WebAppConfiguration
class DepartmentControllerTest {

  public MockMvc mockMvc;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void findAll() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/departments"))
            .andExpect(status().isOk())
            .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    String expected =
        "[{\"departmentId\":2,\"title\":\"string\"},{\"departmentId\":3,\"title\":\"title\"}]";
    Assertions.assertEquals(expected, content);
  }

  @Test
  void saveDepartment() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/departments")
                    .content(asJsonString(new DepartmentRequest("title")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    String expected = "\"title\":\"title\"";
    Assertions.assertTrue(content.contains(expected));
  }

  @Test
  void updateDepartment() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.patch("/departments/{id}", 2L)
                    .content(asJsonString(new DepartmentRequest("title")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    String expected = "\"title\":\"title\"";
    Assertions.assertTrue(content.contains(expected));
  }

  @Test
  void deleteDepartment() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/departments/{id}", 1L))
        .andExpect(status().isNoContent());
  }

  @SneakyThrows
  public static String asJsonString(final Object obj) {
    return new ObjectMapper().writeValueAsString(obj);
  }
}
