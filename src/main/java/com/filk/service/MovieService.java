package com.filk.service;

import com.filk.dto.MoviePostDto;
import com.filk.dto.MoviePutDto;
import com.filk.entity.Movie;
import com.filk.util.RequestParameters;

import java.util.List;

public interface MovieService {
    List<Movie> getAll(RequestParameters requestParameters);
    List<Movie> getRandom();
    List<Movie> getByGenre(int genreId, RequestParameters requestParameters);
    Movie getById(int movieId, RequestParameters requestParameters);
    Movie add(MoviePostDto moviePostDto);
    Movie edit(MoviePutDto moviePutDto);
}
