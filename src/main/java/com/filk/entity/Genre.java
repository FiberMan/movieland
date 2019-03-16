package com.filk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Genre {
    @NonNull
    private int id;
    @NonNull
    private String name;
}
