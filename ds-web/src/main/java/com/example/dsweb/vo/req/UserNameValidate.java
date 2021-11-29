package com.example.dsweb.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserNameValidate {
    @NotBlank(message = "昵称不能为空")
    private String username;
}
