package com.filk.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MoviePutDto {
    private int movieId;
    private String nameRussian;
    private String nameNative;
    private String picturePath;
    private int[] countries;
    private int[] genres;
}