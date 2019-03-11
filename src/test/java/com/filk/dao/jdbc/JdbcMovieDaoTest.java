package com.filk.dao.jdbc;

import com.filk.dao.MovieDao;
import com.filk.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/root-context.xml"})
public class JdbcMovieDaoTest {
    @Autowired
    private MovieDao movieDao;

    @Test
    public void getAll() {
        List<Movie> movies = movieDao.getAll();

        assertNotNull(movies);
        assertEquals(25, movies.size());

        Movie checkMovie = null;

        for (Movie movie : movies) {
            if(movie.getId() == 1) {
                checkMovie = movie;
                break;
            }
        }

        assertNotNull(checkMovie);
        assertEquals("Побег из Шоушенка", checkMovie.getNameRussian());
        assertEquals("The Shawshank Redemption", checkMovie.getNameNative());
        assertEquals("1994", checkMovie.getYearOfRelease());
        assertEquals("США", checkMovie.getCountry());
        assertEquals("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в " +
                "тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. " +
                "Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, " +
                "отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.", checkMovie.getDescription());
        assertEquals("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg", checkMovie.getPicturePath());
        assertEquals(8.9, checkMovie.getRating(), 0.01);
        assertEquals(123.45, checkMovie.getPrice(), 0.01);

        checkMovie = null;

        for (Movie movie : movies) {
            if(movie.getId() == 24) {
                checkMovie = movie;
                break;
            }
        }

        assertNotNull(checkMovie);
        assertEquals("Джанго освобожденный", checkMovie.getNameRussian());
        assertEquals("Django Unchained", checkMovie.getNameNative());
        assertEquals("2012", checkMovie.getYearOfRelease());
        assertEquals("США", checkMovie.getCountry());
        assertEquals("Эксцентричный охотник за головами, также известный как «Дантист», промышляет отстрелом самых опасных преступников. " +
                "Работенка пыльная, и без надежного помощника ему не обойтись. Но как найти такого и желательно не очень дорогого? " +
                "Беглый раб по имени Джанго — прекрасная кандидатура. Правда, у нового помощника свои мотивы — " +
                "кое с чем надо разобраться…", checkMovie.getDescription());
        assertEquals("https://images-na.ssl-images-amazon.com/images/M/MV5BMjIyNTQ5NjQ1OV5BMl5BanBnXkFtZTcwODg1MDU4OA@@._V1._SY209_CR0,0,140,209_.jpg", checkMovie.getPicturePath());
        assertEquals(8.5, checkMovie.getRating(), 0.01);
        assertEquals(170.00, checkMovie.getPrice(), 0.01);
    }
}
