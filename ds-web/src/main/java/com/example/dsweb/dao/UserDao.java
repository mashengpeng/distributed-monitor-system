package com.example.dsweb.dao;

import com.example.dsgeneral.data.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User findUserByName(String name);
    boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteByName(String name);
}
