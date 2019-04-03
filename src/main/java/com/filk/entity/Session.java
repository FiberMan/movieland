package com.filk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Session {
    private String token;
    private User user;
    private LocalDateTime expireDate;
}
