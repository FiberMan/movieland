package com.filk.dao.jdbc;

import com.filk.dao.GenreDao;
import com.filk.entity.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
public class JdbcGenreDaoTest {
    @Autowired
    private GenreDao genreDao;

    @Test
    public void getAll() {
        List<Genre> genres = genreDao.getAll();

        assertNotNull(genres);
        assertEquals(15, genres.size());

        Genre genreCheck = genres.get(0);
        assertEquals(1, genreCheck.getId());
        assertEquals("драма", genreCheck.getName());

        genreCheck = genres.get(12);
        assertEquals(13, genreCheck.getId());
        assertEquals("мультфильм", genreCheck.getName());
    }
}
