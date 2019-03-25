package com.filk.web.controller;

import com.filk.entity.*;
import com.filk.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
@WebAppConfiguration
public class MovieControllerTest {
    private MockMvc mockMvc;
    private MovieService movieServiceMock = mock(MovieService.class);
    private List<Movie> movies = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        reset(movieServiceMock);
        MovieController movieController = new MovieController(movieServiceMock);

        this.mockMvc = standaloneSetup(movieController)
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .build();

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setNameRussian("Кино 1");
        movie1.setNameNative("Movie 1");
        movie1.setYearOfRelease("2010");
        movie1.setDescription("Кино века");
        movie1.setPicturePath("https://picture.url.com/pic.jpg");
        movie1.setRating(8.99);
        movie1.setPrice(188.01);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setNameRussian("Кино 2");
        movie2.setNameNative("Movie 2");
        movie2.setYearOfRelease("2020");
        movie2.setDescription("Кино века 2");
        movie2.setPicturePath("https://picture.url.com/pic2.jpg");
        movie2.setRating(9);
        movie2.setPrice(13);

        Country country1 = new Country(1, "Україна");
        Country country2 = new Country(2, "Норвегія");

        Genre genre1 = new Genre(1, "комедия");
        Genre genre2 = new Genre(2, "мультик");

        User user1 = new User(1, "Вася Пупыч");
        User user2 = new User(2, "Иван Василич");

        Review review1 = new Review(1, user1, "ревью №1");
        Review review2 = new Review(2, user2, "ревью №2");

        movie1.setCountries(Arrays.asList(country1, country2));
        movie1.setGenres(Arrays.asList(genre1, genre2));
        movie1.setReviews(Arrays.asList(review1, review2));

        movies.add(movie1);
        movies.add(movie2);
    }

    @Test
    public void getAllMoviesJson() throws Exception {
        RequestParameters requestParameters = new RequestParameters();
        when(movieServiceMock.getAll(requestParameters)).thenReturn(movies);

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

        verify(movieServiceMock, times(1)).getAll(requestParameters);
        verifyNoMoreInteractions(movieServiceMock);
    }

    @Test
    public void getRandomMoviesJson() throws Exception {
        when(movieServiceMock.getRandom()).thenReturn(movies);

        mockMvc.perform(get("/movie/random"))
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

        verify(movieServiceMock, times(1)).getRandom();
        verifyNoMoreInteractions(movieServiceMock);
    }

    @Test
    public void getMoviesByGenreJson() throws Exception {
        int genreId = 3;
        RequestParameters requestParameters = new RequestParameters();

        when(movieServiceMock.getByGenre(genreId, requestParameters)).thenReturn(movies);

        mockMvc.perform(get("/movie/genre/3"))
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

        verify(movieServiceMock, times(1)).getByGenre(genreId, requestParameters);
        verifyNoMoreInteractions(movieServiceMock);
    }

    @Test
    public void getMovieByIdJson() throws Exception {
        when(movieServiceMock.getById(33)).thenReturn(movies.get(0));

        mockMvc.perform(get("/movie/33"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nameRussian", is("Кино 1")))
                .andExpect(jsonPath("$.nameNative", is("Movie 1")))
                .andExpect(jsonPath("$.yearOfRelease", is("2010")))
                .andExpect(jsonPath("$.picturePath", is("https://picture.url.com/pic.jpg")))
                .andExpect(jsonPath("$.rating", is(8.99)))
                .andExpect(jsonPath("$.price", is(188.01)))

                .andExpect(jsonPath("$.countries", hasSize(2)))
                .andExpect(jsonPath("$.countries[0].id", is(1)))
                .andExpect(jsonPath("$.countries[0].name", is("Україна")))
                .andExpect(jsonPath("$.countries[1].id", is(2)))
                .andExpect(jsonPath("$.countries[1].name", is("Норвегія")))

                .andExpect(jsonPath("$.genres", hasSize(2)))
                .andExpect(jsonPath("$.genres[0].id", is(1)))
                .andExpect(jsonPath("$.genres[0].name", is("комедия")))
                .andExpect(jsonPath("$.genres[1].id", is(2)))
                .andExpect(jsonPath("$.genres[1].name", is("мультик")))

                .andExpect(jsonPath("$.reviews", hasSize(2)))
                .andExpect(jsonPath("$.reviews[0].id", is(1)))
                .andExpect(jsonPath("$.reviews[0].text", is("ревью №1")))
                .andExpect(jsonPath("$.reviews[0].user.id", is(1)))
                .andExpect(jsonPath("$.reviews[0].user.name", is("Вася Пупыч")))
                .andExpect(jsonPath("$.reviews[1].id", is(2)))
                .andExpect(jsonPath("$.reviews[1].text", is("ревью №2")))
                .andExpect(jsonPath("$.reviews[1].user.id", is(2)))
                .andExpect(jsonPath("$.reviews[1].user.name", is("Иван Василич")));
    }
}
