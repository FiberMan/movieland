package com.filk.dao;

import com.filk.entity.User;

public interface UserDao {
    User getByEmail(String email);
}
