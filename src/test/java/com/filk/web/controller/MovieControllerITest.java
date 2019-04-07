package com.filk.web.controller;

import com.filk.config.AppConfig;
import com.filk.config.MvcConfig;
import com.filk.config.TestConfig;
import com.filk.entity.*;
import com.filk.service.MovieService;
import com.filk.service.SecurityService;
import com.filk.service.impl.DefaultMovieService;
import com.filk.util.RequestParameters;
import com.filk.util.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, MvcConfig.class, TestConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class MovieControllerITest {
    private MockMvc mockMvc;
    private MovieService movieServiceMock = mock(DefaultMovieService.class);

    @Autowired
    private SecurityService securityService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private List<Movie> movies = new ArrayList<>();

    public void setup() throws Exception {
        MovieController movieController = new MovieController(movieServiceMock);
        mockMvc = standaloneSetup(movieController)
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

        Movie movieR = new Movie();
        movieR.setId(1);

        Review review1 = new Review(1, movieR, user1, "ревью №1");
        Review review2 = new Review(2, movieR, user2, "ревью №2");

        movie1.setCountries(Arrays.asList(country1, country2));
        movie1.setGenres(Arrays.asList(genre1, genre2));
        movie1.setReviews(Arrays.asList(review1, review2));

        movies.add(movie1);
        movies.add(movie2);
    }

    public void setup2() {
        reset(securityService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllMoviesJson() throws Exception {
        setup();

        RequestParameters requestParameters = new RequestParameters();
        when(movieServiceMock.getAll(requestParameters)).thenReturn(movies);

        mockMvc.perform(get("/movie"))
                .andDo(print())
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
        setup();

        when(movieServiceMock.getRandom()).thenReturn(movies);

        mockMvc.perform(get("/movie/random"))
                .andDo(print())
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
        setup();

        int genreId = 3;
        RequestParameters requestParameters = new RequestParameters();

        when(movieServiceMock.getByGenre(genreId, requestParameters)).thenReturn(movies);

        mockMvc.perform(get("/movie/genre/3"))
                .andDo(print())
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
        setup();

        when(movieServiceMock.getById(33, new RequestParameters())).thenReturn(movies.get(0));

        mockMvc.perform(get("/movie/33"))
                .andDo(print())
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

    @Test
    public void addMovieSuccess() throws Exception {
        setup2();

        User user = User.newBuilder()
                .setId(1)
                .setName("User Name")
                .setRole(UserRole.ADMIN)
                .setEmail("email@gmail.com")
                .setHash("jno83iwhjhj")
                .build();
        Session session = new Session("user_token", user, LocalDateTime.now().plusHours(2));

        when(securityService.getSession("user_token")).thenReturn(Optional.of(session));
        when(securityService.checkPermission(user, Collections.singletonList(UserRole.ADMIN))).thenReturn(true);

        String movieJson = "{\n" +
                "        \"nameRussian\": \"Тестовое кино\",\n" +
                "        \"nameNative\": \"Test movie\",\n" +
                "        \"yearOfRelease\": \"1994\",\n" +
                "        \"description\": \"Успешный банкир...\",\n" +
                "        \"price\": 123.45,\n" +
                "        \"picturePath\": \"https://images-na.ssl-images-amazon.com/abc.jpg\",\n" +
                "        \"countries\": [1,2],\n" +
                "        \"genres\": [1,2,3]\n" +
                "}";

        mockMvc.perform(post("/movie")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(movieJson)
                .header("uuid", "user_token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(26)))
                .andExpect(jsonPath("$.nameRussian", is("Тестовое кино")))
                .andExpect(jsonPath("$.nameNative", is("Test movie")))
                .andExpect(jsonPath("$.yearOfRelease", is("1994")))
                .andExpect(jsonPath("$.description", is("Успешный банкир...")))
                .andExpect(jsonPath("$.price", is(123.45)))
                .andExpect(jsonPath("$.picturePath", is("https://images-na.ssl-images-amazon.com/abc.jpg")))
                .andExpect(jsonPath("$.rating", is(0.0)))
                .andExpect(jsonPath("$.countries", hasSize(2)))
                .andExpect(jsonPath("$.genres", hasSize(3)));
    }

    @Test
    public void editMovieSuccess() throws Exception {
        setup2();

        User user = User.newBuilder()
                .setId(1)
                .setName("User Name")
                .setRole(UserRole.ADMIN)
                .setEmail("email@gmail.com")
                .setHash("jno83iwhjhj")
                .build();
        Session session = new Session("user_token", user, LocalDateTime.now().plusHours(2));

        when(securityService.getSession("user_token")).thenReturn(Optional.of(session));
        when(securityService.checkPermission(user, Collections.singletonList(UserRole.ADMIN))).thenReturn(true);

        String movieJson = "{\n" +
                "        \"nameRussian\": \"Исправленное название\",\n" +
                "        \"nameNative\": \"Edited name\",\n" +
                "        \"picturePath\": \"https://images-na.ssl-images-amazon.com/images/1.jpg\",\n" +
                "        \"countries\": [1,2],\n" +
                "        \"genres\": [1,2,3]\n" +
                "}";

        mockMvc.perform(put("/movie/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(movieJson)
                .header("uuid", "user_token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nameRussian", is("Исправленное название")))
                .andExpect(jsonPath("$.nameNative", is("Edited name")))
                .andExpect(jsonPath("$.yearOfRelease", is("1994")))
                .andExpect(jsonPath("$.description", is("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.")))
                .andExpect(jsonPath("$.price", is(123.45)))
                .andExpect(jsonPath("$.picturePath", is("https://images-na.ssl-images-amazon.com/images/1.jpg")))
                .andExpect(jsonPath("$.rating", is(8.9)));
    }
}
