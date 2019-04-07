package com.filk.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MoviePostDto {
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private String description;
    private double price;
    private String picturePath;
    private int[] countries;
    private int[] genres;
}