package com.example.dsweb.service.impl;

import com.example.dsgeneral.data.User;
import com.example.dsweb.dao.UserDao;
import com.example.dsweb.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public boolean insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteByName(String name) {
        return userDao.deleteByName(name);
    }
}
