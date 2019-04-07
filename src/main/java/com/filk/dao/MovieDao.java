package com.filk.dao;

import com.filk.dto.MoviePostDto;
import com.filk.dto.MoviePutDto;
import com.filk.entity.Movie;
import com.filk.util.RequestParameters;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll(RequestParameters requestParameters);
    List<Movie> getRandom();
    List<Movie> getByGenre(int genreId, RequestParameters requestParameters);
    Movie getById(int movieId);
    Movie add(MoviePostDto moviePostDto);
    Movie edit(MoviePutDto moviePutDto, Movie movieToEdit);
}
