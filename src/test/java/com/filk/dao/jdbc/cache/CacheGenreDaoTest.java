package com.filk.dao.jdbc.cache;

import com.filk.dao.GenreDao;
import com.filk.dao.cache.CacheGenreDao;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CacheGenreDaoTest {
    @Test
    public void getAll() {
        GenreDao genreDaoMock = mock(GenreDao.class);
        CacheGenreDao cacheGenreDao = new CacheGenreDao();

        when(genreDaoMock.getAll()).thenReturn(null);

        cacheGenreDao.setGenreDao(genreDaoMock);
        cacheGenreDao.refreshGenresCache();
        cacheGenreDao.getAll();

        verify(genreDaoMock, times(1)).getAll();
        verifyNoMoreInteractions(genreDaoMock);
    }
}
