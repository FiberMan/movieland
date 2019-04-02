package com.filk.service;

import com.filk.entity.User;

public interface UserService {
    User getByEmail(String email);
}
