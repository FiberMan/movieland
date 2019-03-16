package com.filk.web.controller;

import com.filk.entity.Genre;
import com.filk.service.GenreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
@WebAppConfiguration
public class GenreControllerTest {
    private MockMvc mockMvc;
    private GenreService genreServiceMock = mock(GenreService.class);
    private List<Genre> genres = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        GenreController genreController = new GenreController(genreServiceMock);

        this.mockMvc = standaloneSetup(genreController)
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .build();

        Genre genre1 = new Genre();
        genre1.setId(1);
        genre1.setName("фантастика");

        Genre genre2 = new Genre();
        genre2.setId(2);
        genre2.setName("приключения");

        genres.add(genre1);
        genres.add(genre2);
    }

    @Test
    public void getAllGenresJson () throws Exception {
        when(genreServiceMock.getAll()).thenReturn(genres);

        mockMvc.perform(get("/genre"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("фантастика")))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("приключения")));

        verify(genreServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(genreServiceMock);
    }
}
