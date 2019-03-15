package com.filk.dao.jdbc;

import com.filk.dao.MovieDao;
import com.filk.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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

        Movie checkMovie = movies.get(0);

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

        checkMovie = movies.get(23);

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

    @Test
    public void getAllSortByRatingAsc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "rating");
        parameters.put("sortOrder", "asc");

        List<Movie> movies = movieDao.getAll(parameters);

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Блеф", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByRatingDesc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "rating");
        parameters.put("sortOrder", "desc");

        List<Movie> movies = movieDao.getAll(parameters);

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Побег из Шоушенка", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByRatingDefault() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "rating");

        List<Movie> movies = movieDao.getAll(parameters);

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Побег из Шоушенка", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByPriceAsc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "price");
        parameters.put("sortOrder", "asc");

        List<Movie> movies = movieDao.getAll(parameters);

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Блеф", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByPriceDesc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "price");
        parameters.put("sortOrder", "desc");

        List<Movie> movies = movieDao.getAll(parameters);

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Форрест Гамп", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByPriceDefault() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "price");

        List<Movie> movies = movieDao.getAll(parameters);

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Блеф", movies.get(0).getNameRussian());
    }

    @Test
    public void getRandom() {
        List<Movie> movies = movieDao.getRandom(3);

        assertNotNull(movies);
        assertEquals(3, movies.size());

        String movieIds1 = movies.get(0).getId() + "_" + movies.get(1).getId() + "_" + movies.get(2).getId();

        movies = movieDao.getRandom(3);

        assertNotNull(movies);
        assertEquals(3, movies.size());

        String movieIds2 = movies.get(0).getId() + "_" + movies.get(1).getId() + "_" + movies.get(2).getId();

        assertNotEquals(movieIds1, movieIds2);
    }

    @Test
    public void getByGenre() {
        List<Movie> movies;

        movies = movieDao.getByGenre(1);
        assertNotNull(movies);
        assertEquals(16, movies.size());
        assertEquals("Побег из Шоушенка", movies.get(0).getNameRussian());
        assertEquals("Гладиатор", movies.get(10).getNameRussian());
        assertEquals("Танцующий с волками", movies.get(15).getNameRussian());

        movies = movieDao.getByGenre(5);
        assertNotNull(movies);
        assertEquals(3, movies.size());
        assertEquals("Форрест Гамп", movies.get(0).getNameRussian());
        assertEquals("Жизнь прекрасна", movies.get(1).getNameRussian());
        assertEquals("Титаник", movies.get(2).getNameRussian());
    }

    @Test
    public void getByGenreSortByRatingAsc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "rating");
        parameters.put("sortOrder", "asc");
        parameters.put("genreId", 9);

        List<Movie> movies = movieDao.getByGenre(parameters);

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Звёздные войны: Эпизод 4 – Новая надежда", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByRatingDesc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "rating");
        parameters.put("sortOrder", "desc");
        parameters.put("genreId", 9);

        List<Movie> movies = movieDao.getByGenre(parameters);

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByRatingDefault() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "rating");
        parameters.put("genreId", 9);

        List<Movie> movies = movieDao.getByGenre(parameters);

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByPriceAsc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "price");
        parameters.put("sortOrder", "asc");
        parameters.put("genreId", 9);

        List<Movie> movies = movieDao.getByGenre(parameters);

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByPriceDesc() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "price");
        parameters.put("sortOrder", "desc");
        parameters.put("genreId", 9);

        List<Movie> movies = movieDao.getByGenre(parameters);

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Темный рыцарь", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByPriceDefault() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sortBy", "price");
        parameters.put("genreId", 9);

        List<Movie> movies = movieDao.getByGenre(parameters);

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }
}
