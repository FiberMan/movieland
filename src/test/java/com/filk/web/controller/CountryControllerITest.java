package com.filk.web.controller;

import com.filk.config.AppConfig;
import com.filk.entity.Country;
import com.filk.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
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
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class CountryControllerITest {
    private MockMvc mockMvc;
    private CountryService countryServiceMock = mock(CountryService.class);
    private List<Country> countries = new ArrayList<>();

    @Before
    public void setup() {
        CountryController countryController = new CountryController(countryServiceMock);

        mockMvc = standaloneSetup(countryController)
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .build();

        countries.add(new Country(1, "Україна"));
        countries.add(new Country(2, "Мувіляндія"));
    }

    @Test
    public void getAllCountries() throws Exception {
        when(countryServiceMock.getAll()).thenReturn(countries);

        mockMvc.perform(get("/country"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Україна")))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Мувіляндія")));

        verify(countryServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(countryServiceMock);
    }
}