package com.filk.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.filk.view.Views;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Movie {
    @NonNull
    @JsonView(Views.Movie.class)
    private int id;

    @NonNull
    @JsonView(Views.Movie.class)
    private String nameRussian;

    @NonNull
    @JsonView(Views.Movie.class)
    private String nameNative;

    @JsonView(Views.Movie.class)
    private String yearOfRelease;

    @JsonView(Views.Movie.class)
    private String picturePath;

    @JsonView(Views.Movie.class)
    private double rating;

    @JsonView(Views.Movie.class)
    private double price;

    @JsonView(Views.MovieDetail.class)
    private String description;

    @JsonView(Views.MovieDetail.class)
    private List<Country> countries;

    @JsonView(Views.MovieDetail.class)
    private List<Genre> genres;

    @JsonView(Views.MovieDetail.class)
    private List<Review> reviews;
}
