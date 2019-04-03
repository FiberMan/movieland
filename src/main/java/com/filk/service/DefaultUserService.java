package com.filk.service;

import com.filk.dao.UserDao;
import com.filk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    private UserDao userDao;

    @Autowired
    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }
}
