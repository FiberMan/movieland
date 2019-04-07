package com.filk.dao.jdbc;

import com.filk.config.AppConfig;
import com.filk.dao.MovieDao;
import com.filk.entity.Movie;
import com.filk.util.RequestParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class JdbcMovieDaoITest {
    @Autowired
    private MovieDao movieDao;

    @Test
    public void getAll() {
        List<Movie> movies = movieDao.getAll(new RequestParameters());

        assertNotNull(movies);
        assertEquals(25, movies.size());

        Movie checkMovie = movies.get(0);

        assertNotNull(checkMovie);
        assertEquals("Побег из Шоушенка", checkMovie.getNameRussian());
        assertEquals("The Shawshank Redemption", checkMovie.getNameNative());
        assertEquals("1994", checkMovie.getYearOfRelease());
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
        assertEquals("Эксцентричный охотник за головами, также известный как «Дантист», промышляет отстрелом самых опасных преступников. " +
                "Работенка пыльная, и без надежного помощника ему не обойтись. Но как найти такого и желательно не очень дорогого? " +
                "Беглый раб по имени Джанго — прекрасная кандидатура. Правда, у нового помощника свои мотивы — " +
                "кое с чем надо разобраться…", checkMovie.getDescription());
        assertEquals("https://images-na.ssl-images-amazon.com/images/M/MV5BMjIyNTQ5NjQ1OV5BMl5BanBnXkFtZTcwODg1MDU4OA@@._V1._SY209_CR0,0,140,209_.jpg", checkMovie.getPicturePath());
        assertEquals(8.5, checkMovie.getRating(), 0.01);
        assertEquals(170.00, checkMovie.getPrice(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllSortByRatingAsc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("rating");
        requestParameters.setSortOrder("asc");

        List<Movie> movies = movieDao.getAll(requestParameters.postProcess());
    }

    @Test
    public void getAllSortByRatingDesc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("rating");
        requestParameters.setSortOrder("desc");

        List<Movie> movies = movieDao.getAll(requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Побег из Шоушенка", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByRatingDefault() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("rating");

        List<Movie> movies = movieDao.getAll(requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Побег из Шоушенка", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByPriceAsc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("price");
        requestParameters.setSortOrder("asc");

        List<Movie> movies = movieDao.getAll(requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Блеф", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByPriceDesc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("price");
        requestParameters.setSortOrder("desc");

        List<Movie> movies = movieDao.getAll(requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Форрест Гамп", movies.get(0).getNameRussian());
    }

    @Test
    public void getAllSortByPriceDefault() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("price");

        List<Movie> movies = movieDao.getAll(requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(25, movies.size());
        assertEquals("Блеф", movies.get(0).getNameRussian());
    }

    @Test
    public void getRandom() {
        List<Movie> movies;

        movies = movieDao.getRandom();
        assertNotNull(movies);
        assertEquals(3, movies.size());
    }

    @Test
    public void getByGenre() {
        List<Movie> movies = movieDao.getByGenre(5, new RequestParameters());

        assertNotNull(movies);
        assertEquals(3, movies.size());
        assertEquals("Форрест Гамп", movies.get(0).getNameRussian());
        assertEquals("Жизнь прекрасна", movies.get(1).getNameRussian());
        assertEquals("Титаник", movies.get(2).getNameRussian());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByGenreSortByRatingAsc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("rating");
        requestParameters.setSortOrder("asc");

        List<Movie> movies = movieDao.getByGenre(9, requestParameters.postProcess());
    }

    @Test
    public void getByGenreSortByRatingDesc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("rating");
        requestParameters.setSortOrder("desc");

        List<Movie> movies = movieDao.getByGenre(9, requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByRatingDefault() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("rating");

        List<Movie> movies = movieDao.getByGenre(9, requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByPriceAsc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("price");
        requestParameters.setSortOrder("asc");

        List<Movie> movies = movieDao.getByGenre(9, requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByPriceDesc() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("price");
        requestParameters.setSortOrder("desc");

        List<Movie> movies = movieDao.getByGenre(9, requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Темный рыцарь", movies.get(0).getNameRussian());
    }

    @Test
    public void getByGenreSortByPriceDefault() {
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setSortBy("price");

        List<Movie> movies = movieDao.getByGenre(9, requestParameters.postProcess());

        assertNotNull(movies);
        assertEquals(5, movies.size());
        assertEquals("Начало", movies.get(0).getNameRussian());
    }
}
