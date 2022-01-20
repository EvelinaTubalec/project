package controller;

import com.leverx.config.ApplicationConfig;
import org.apache.commons.codec.binary.Base64;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@WebAppConfiguration
public class BasicAuthTest {

    public MockMvc mockMvc;

    @Autowired private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void authWithHeaderTest() throws Exception {
      String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("<user>:<password>").getBytes()));
      mockMvc
                .perform(MockMvcRequestBuilders.get("/employees")
                .header("Authorization", basicDigestHeaderValue)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void accessProtected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loginUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees").with(httpBasic("user", "password")))
                .andExpect(authenticated());
    }

    @Test
    public void loginInvalidUser() throws Exception {
        MvcResult result = mockMvc.perform(formLogin().user("invalid").password("invalid"))
                .andExpect(unauthenticated())
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("HTTP Status 401"));
    }
}
