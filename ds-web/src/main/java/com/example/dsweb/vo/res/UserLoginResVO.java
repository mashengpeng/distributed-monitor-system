package com.example.dsweb.vo.res;

import lombok.Data;

/**
 * 用户登录后返回的
 */
@Data
public class UserLoginResVO {
    
    private Long uid;
    private String username;
    private String sid;
    
}
