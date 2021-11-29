package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class User {
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String pwd;
}
