package com.example.dsweb.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientByIpReqVO {
    @NotBlank(message = "ip不能为空")
    private String ip;
}
