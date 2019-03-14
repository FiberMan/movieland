package com.filk.service.impl;

import com.filk.dao.GenreDao;
import com.filk.entity.Genre;
import com.filk.service.GenreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
public class DefaultGenreServiceTest {
    private GenreDao genreDaoMock = mock(GenreDao.class);

    @Autowired
    private GenreService genreService;

    @Test
    public void getAll() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("мультфильм");

        List<Genre> genres = new ArrayList<>();
        genres.add(genre);

        when(genreDaoMock.getAll()).thenReturn(genres);
        genreService.setGenreDao(genreDaoMock);

        List<Genre> all;
        all = genreService.getAll();
        all = genreService.getAll();
        all = genreService.getAll();

        verify(genreDaoMock, times(1)).getAll();
        verifyNoMoreInteractions(genreDaoMock);
    }
}
