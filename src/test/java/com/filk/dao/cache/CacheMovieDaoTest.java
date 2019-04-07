package com.filk.dao.cache;

import com.filk.dao.MovieDao;
import com.filk.dto.MoviePostDto;
import com.filk.dto.MoviePutDto;
import com.filk.entity.Movie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CacheMovieDaoTest {
    private CacheMovieDao cacheMovieDao = new CacheMovieDao();
    private MovieDao movieDaoMock = mock(MovieDao.class);

    @Before
    public void setup() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setNameNative("Movie Test");
        movie.setNameRussian("Кино тест");

        when(movieDaoMock.getById(1)).thenReturn(movie);
        when(movieDaoMock.add(any())).thenReturn(movie);
        when(movieDaoMock.edit(any(), any())).thenReturn(movie);

        cacheMovieDao.setMovieDao(movieDaoMock);
    }

    @Test
    public void getById() {
        Movie movieDb = cacheMovieDao.getById(1);
        assertEquals(1, movieDb.getId());
        assertEquals("Movie Test", movieDb.getNameNative());
        assertEquals("Кино тест", movieDb.getNameRussian());

        Movie movieCache = cacheMovieDao.getById(1);
        assertEquals(1, movieCache.getId());
        assertEquals("Movie Test", movieCache.getNameNative());
        assertEquals("Кино тест", movieCache.getNameRussian());

        verify(movieDaoMock, times(1)).getById(anyInt());
        verifyNoMoreInteractions(movieDaoMock);
    }

    @Test
    public void add() {
        Movie movie = cacheMovieDao.add(new MoviePostDto());

        Movie movieCache = cacheMovieDao.getById(1);
        assertEquals(1, movieCache.getId());
        assertEquals("Movie Test", movieCache.getNameNative());
        assertEquals("Кино тест", movieCache.getNameRussian());

        verify(movieDaoMock, times(1)).add(any());
        verify(movieDaoMock, times(0)).getById(anyInt());
        verifyNoMoreInteractions(movieDaoMock);
    }

    @Test
    public void edit() {
        Movie movie = cacheMovieDao.edit(new MoviePutDto(), new Movie());

        Movie movieCache = cacheMovieDao.getById(1);
        assertEquals(1, movieCache.getId());
        assertEquals("Movie Test", movieCache.getNameNative());
        assertEquals("Кино тест", movieCache.getNameRussian());

        verify(movieDaoMock, times(1)).edit(any(), any());
        verify(movieDaoMock, times(0)).getById(anyInt());
        verifyNoMoreInteractions(movieDaoMock);
    }
}