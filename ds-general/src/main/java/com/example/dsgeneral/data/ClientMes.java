package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class ClientMes {
    /**
     * 自增id
     */
    private Integer id;
    private String host;
    private String time;
    private String osName;
}
