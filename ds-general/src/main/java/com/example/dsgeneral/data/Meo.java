package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class Meo {
    /**
     * 自增id
     */
    private Integer id;
    private String host;
    /**
     * 物理内存空闲率
     */
    private String available;

    /**
     * 虚拟内存使用率
     */
    private String virInuse;
}
