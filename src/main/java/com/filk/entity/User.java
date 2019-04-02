package com.filk.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.filk.util.UserRole;
import com.filk.util.Views;
import lombok.*;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @NonNull
    @JsonView(Views.Base.class)
    private int id;

    @NonNull
    @JsonView(Views.Base.class)
    private String name;

    private String email;

    private UserRole role;

    private String hash;

    private String salt;
}
