package com.filk.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.filk.util.UserRole;
import com.filk.util.Views;
import lombok.*;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
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

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(int id) {
            User.this.id = id;

            return this;
        }

        public Builder setName(String name) {
            User.this.name = name;

            return this;
        }

        public Builder setEmail(String email) {
            User.this.email = email;

            return this;
        }

        public Builder setRole(UserRole role) {
            User.this.role = role;

            return this;
        }

        public Builder setHash(String hash) {
            User.this.hash = hash;

            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
