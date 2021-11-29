package com.example.dsweb.service;

import com.example.dsgeneral.data.User;

public interface UserService {
    User findUserByName(String name);
    boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteByName(String name);
}
