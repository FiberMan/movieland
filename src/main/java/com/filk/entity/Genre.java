package com.filk.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.filk.util.Views;
import lombok.*;


@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Genre {
    @NonNull
    @JsonView(Views.Base.class)
    private int id;

    @NonNull
    @JsonView(Views.Base.class)
    private String name;
}
