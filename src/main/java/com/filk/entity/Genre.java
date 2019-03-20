package com.filk.entity;

import lombok.*;


@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Genre {
    @NonNull
    private int id;
    @NonNull
    private String name;
}
