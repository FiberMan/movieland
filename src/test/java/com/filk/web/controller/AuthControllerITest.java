package com.filk.web.controller;

import com.filk.config.AppConfig;
import com.filk.config.RestResponseEntityExceptionHandler;
import com.filk.service.SecurityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class AuthControllerITest {
    private MockMvc mockMvc;

    @Before
    public void setup() {
        AuthController authController = new AuthController(securityService);

        mockMvc = standaloneSetup(authController)
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

    }

    @Autowired
    private SecurityService securityService;

    @Test
    public void loginSuccessfull() throws Exception {
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\" : \"ronald.reynolds66@example.com\",\"password\" : \"paco\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andExpect(jsonPath("$.nickname", is("Рональд Рейнольдс")));
    }

    @Test
    public void loginWrongEmail() throws Exception {
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\" : \"ronald@example.com\",\"password\" : \"paco\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginWrongPassword() throws Exception {
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\" : \"ronald.reynolds66@example.com\",\"password\" : \"pacokdhfdjh\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(post("/logout")
                .header("uuid", "uuid_value"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}