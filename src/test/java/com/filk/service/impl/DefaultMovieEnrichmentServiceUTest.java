package com.filk.service.impl;

import com.filk.entity.*;
import com.filk.service.CountryService;
import com.filk.service.GenreService;
import com.filk.service.ReviewService;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultMovieEnrichmentServiceUTest {

    @Test
    public void enrich() {
        CountryService countryServiceMock = mock(CountryService.class);
        GenreService genreServiceMock = mock(GenreService.class);
        ReviewService reviewServiceMock = mock(ReviewService.class);

        Country country1 = new Country(1, "Україна");
        Country country2 = new Country(2, "Норвегія");

        Genre genre1 = new Genre(1, "комедия");
        Genre genre2 = new Genre(2, "мультик");

        User user1 = new User(1, "Вася Пупыч");
        User user2 = new User(2, "Иван Василич");

        Movie movie1 = new Movie();
        movie1.setId(33);

        Review review1 = new Review(1, movie1, user1, "ревью №1");
        Review review2 = new Review(2, movie1, user2, "ревью №2");

        Movie movie = new Movie(33, "Очень Джава Кино", "Very Java Movie");


        when(countryServiceMock.getByMovieId(33)).thenReturn(Arrays.asList(country1, country2));
        when(genreServiceMock.getByMovieId(33)).thenReturn(Arrays.asList(genre1, genre2));
        when(reviewServiceMock.getByMovieId(33)).thenReturn(Arrays.asList(review1, review2));

        DefaultMovieEnrichmentService enrichmentService = new DefaultMovieEnrichmentService(countryServiceMock, genreServiceMock, reviewServiceMock);
        enrichmentService.enrich(movie);

        assertNotNull(movie.getCountries());
        assertNotNull(movie.getGenres());
        assertNotNull(movie.getReviews());

        assertEquals(2, movie.getCountries().size());
        assertEquals(2, movie.getGenres().size());
        assertEquals(2, movie.getReviews().size());

        assertEquals(1, movie.getCountries().get(0).getId());
        assertEquals("Україна", movie.getCountries().get(0).getName());
        assertEquals(2, movie.getCountries().get(1).getId());
        assertEquals("Норвегія", movie.getCountries().get(1).getName());

        assertEquals(1, movie.getGenres().get(0).getId());
        assertEquals("комедия", movie.getGenres().get(0).getName());
        assertEquals(2, movie.getGenres().get(1).getId());
        assertEquals("мультик", movie.getGenres().get(1).getName());

        assertEquals(1, movie.getReviews().get(0).getId());
        assertEquals("ревью №1", movie.getReviews().get(0).getText());
        assertEquals(1, movie.getReviews().get(0).getUser().getId());
        assertEquals("Вася Пупыч", movie.getReviews().get(0).getUser().getName());
        assertEquals(2, movie.getReviews().get(1).getId());
        assertEquals("ревью №2", movie.getReviews().get(1).getText());
        assertEquals(2, movie.getReviews().get(1).getUser().getId());
        assertEquals("Иван Василич", movie.getReviews().get(1).getUser().getName());
    }
}