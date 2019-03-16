package com.filk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Movie {
    @NonNull
    private int id;
    @NonNull
    private String nameRussian;
    @NonNull
    private String nameNative;
    private String yearOfRelease;
    @JsonIgnore
    private String country;
    @JsonIgnore
    private String description;
    private String picturePath;
    private double rating;
    private double price;
}
