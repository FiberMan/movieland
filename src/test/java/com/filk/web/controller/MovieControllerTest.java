package com.filk.web.controller;

import com.filk.service.MovieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/root-context.xml"})
@WebAppConfiguration
public class MovieControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        MovieService movieServiceMock = mock(MovieService.class);
        when(movieServiceMock.getAll()).thenReturn(new ArrayList<>());

        MovieController movieController = new MovieController(movieServiceMock);

        this.mockMvc = standaloneSetup(movieController)
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                .build();
    }

    @Test
    public void getAllMoviesJson() throws Exception {
        this.mockMvc.perform(get("/api/v1/movie")).andDo(print());

        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/movie"))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Hello World!!!"))
                .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }
}
