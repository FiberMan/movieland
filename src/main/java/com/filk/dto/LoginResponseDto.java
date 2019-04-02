package com.filk.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponseDto {
    @JsonView
    private String uuid;
    @JsonView
    private String nickname;
}
