package com.filk.dao.cache;

import com.filk.dao.MovieDao;
import com.filk.dto.MoviePostDto;
import com.filk.dto.MoviePutDto;
import com.filk.entity.Movie;
import com.filk.util.RequestParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Repository
@Primary
public class CacheMovieDao implements MovieDao {
    private Map<Integer, Movie> movieCache = Collections.synchronizedMap(new WeakHashMap<>());
    private MovieDao movieDao;

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll(RequestParameters requestParameters) {
        List<Movie> movies = movieDao.getAll(requestParameters);
        for (Movie movie : movies) {
            movieCache.put(movie.getId(), movie);
        }
        return movies;
    }

    @Override
    public List<Movie> getRandom() {
        List<Movie> movies = movieDao.getRandom();
        for (Movie movie : movies) {
            movieCache.put(movie.getId(), movie);
        }
        return movies;
    }

    @Override
    public List<Movie> getByGenre(int genreId, RequestParameters requestParameters) {
        List<Movie> movies = movieDao.getByGenre(genreId, requestParameters);
        for (Movie movie : movies) {
            movieCache.put(movie.getId(), movie);
        }
        return movies;
    }

    @Override
    public Movie getById(int movieId) {
        Movie movie = movieCache.get(movieId);
        if (movie == null) {
            movie = movieDao.getById(movieId);
            movieCache.put(movieId, movie);
        }

        return movie;
    }

    @Override
    public Movie add(MoviePostDto moviePostDto) {
        Movie movie = movieDao.add(moviePostDto);
        movieCache.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public Movie edit(MoviePutDto moviePutDto, Movie movieToEdit) {
        Movie movie = movieDao.edit(moviePutDto, movieToEdit);
        movieCache.put(movie.getId(), movie);
        return movie;
    }
}
