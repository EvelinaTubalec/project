package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.leverx.config.ApplicationConfig;
import com.leverx.model.dto.request.ProjectPositionRequestDto;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@WebAppConfiguration
class ProjectPositionControllerTest {

  public MockMvc mockMvc;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void findAll() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/positions")).andExpect(status().isOk());
  }

  @Test
  void saveProjectPosition() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/positions")
                .content(
                    asJsonString(new ProjectPositionRequestDto(2L, 1L, LocalDate.now(), LocalDate.now())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void updateProjectPosition() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/positions/{id}", 1L)
                .content(
                    asJsonString(new ProjectPositionRequestDto(2L, 1L, LocalDate.now(), LocalDate.now())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void deleteProjectPosition() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/positions/{id}", 3L))
        .andExpect(status().isNoContent());
  }

  @SneakyThrows
  public static String asJsonString(final Object obj) {
    return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
  }
}
