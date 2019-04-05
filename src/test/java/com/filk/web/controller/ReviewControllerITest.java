package com.filk.web.controller;

import com.filk.config.AppConfig;
import com.filk.config.MvcConfig;
import com.filk.config.TestConfig;
import com.filk.entity.Session;
import com.filk.entity.User;
import com.filk.service.SecurityService;
import com.filk.util.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, MvcConfig.class, TestConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ReviewControllerITest {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        reset(securityService);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addMovieReviewSuccess() throws Exception {
        User user = User.newBuilder()
                .setId(1)
                .setName("User Name")
                .setRole(UserRole.USER)
                .setEmail("email@gmail.com")
                .setHash("jno83iwhjhj")
                .build();
        Session session = new Session("user_token", user, LocalDateTime.now().plusHours(2));

        when(securityService.getSession("user_token")).thenReturn(Optional.of(session));
        when(securityService.checkPermission(user, Collections.singletonList(UserRole.USER))).thenReturn(true);


        mockMvc.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"movieId\" : 11,\"text\" : \"Тестовое ревью 11\"}")
                .header("uuid", "user_token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.movie.id", is(11)))
                .andExpect(jsonPath("$.text", is("Тестовое ревью 11")))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is("User Name")))
                .andExpect(jsonPath("$.user.email", is("email@gmail.com")))
                .andExpect(jsonPath("$.user.role", is("USER")))
                .andExpect(jsonPath("$.user.hash", is("jno83iwhjhj")));
    }

    @Test
    public void addMovieReviewNotAuthenticated() throws Exception {
        when(securityService.getSession("user_token")).thenReturn(Optional.empty());

        mockMvc.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"movieId\" : 11,\"text\" : \"Тестовое ревью 11\"}")
                .header("uuid", "user_token"))
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void addMovieReviewNotAuthorized() throws Exception {
        User user = User.newBuilder()
                .setId(1)
                .setName("User Name")
                .setRole(UserRole.GUEST)
                .setEmail("email@gmail.com")
                .setHash("jno83iwhjhj")
                .build();
        Session session = new Session("user_token", user, LocalDateTime.now().plusHours(2));

        when(securityService.getSession("user_token")).thenReturn(Optional.of(session));
        when(securityService.checkPermission(user, Collections.singletonList(UserRole.USER))).thenReturn(false);

        mockMvc.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"movieId\" : 11,\"text\" : \"Тестовое ревью 11\"}")
                .header("uuid", "user_token"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}