package com.filk.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.filk.view.Views;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Review {
    @NonNull
    @JsonView(Views.Base.class)
    private int id;

    @NonNull
    @JsonView(Views.Base.class)
    private User user;

    @NonNull
    @JsonView(Views.Base.class)
    private String text;
}
