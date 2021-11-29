package com.example.dsweb.vo.req;

import com.example.dsgeneral.vo.req.BaseReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 密码登录
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginPwdReqVO extends BaseReqVO {

    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
    
}
