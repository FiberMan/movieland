package com.filk.web.controller;

import com.filk.entity.Movie;
import com.filk.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/root-context.xml"})
@WebAppConfiguration
public class MovieControllerTest {
    private MockMvc mockMvc;
    private MovieService movieServiceMock = mock(MovieService.class);

    @Before
    public void setup() throws Exception {
        MovieController movieController = new MovieController(movieServiceMock);

        this.mockMvc = standaloneSetup(movieController)
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .build();
    }

    @Test
    public void getAllMoviesJson() throws Exception {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setNameRussian("Кино 1");
        movie1.setNameNative("Movie 1");
        movie1.setYearOfRelease("2010");
        movie1.setCountry("Ukraine");
        movie1.setDescription("Кино века");
        movie1.setPicturePath("https://picture.url.com/pic.jpg");
        movie1.setRating(8.99);
        movie1.setPrice(188.01);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setNameRussian("Кино 2");
        movie2.setNameNative("Movie 2");
        movie2.setYearOfRelease("2020");
        movie2.setCountry("Mars");
        movie2.setDescription("Кино века 2");
        movie2.setPicturePath("https://picture.url.com/pic2.jpg");
        movie2.setRating(9);
        movie2.setPrice(13);

        when(movieServiceMock.getAll()).thenReturn(Arrays.asList(movie1, movie2));

        mockMvc.perform(get("/movie"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameRussian", is("Кино 1")))
                .andExpect(jsonPath("$[0].nameNative", is("Movie 1")))
                .andExpect(jsonPath("$[0].yearOfRelease", is("2010")))
                .andExpect(jsonPath("$[0].picturePath", is("https://picture.url.com/pic.jpg")))
                .andExpect(jsonPath("$[0].rating", is(8.99)))
                .andExpect(jsonPath("$[0].price", is(188.01)))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nameRussian", is("Кино 2")))
                .andExpect(jsonPath("$[1].nameNative", is("Movie 2")))
                .andExpect(jsonPath("$[1].yearOfRelease", is("2020")))
                .andExpect(jsonPath("$[1].picturePath", is("https://picture.url.com/pic2.jpg")))
                .andExpect(jsonPath("$[1].rating", is(9.0)))
                .andExpect(jsonPath("$[1].price", is(13.0)));

        verify(movieServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(movieServiceMock);
    }
}
