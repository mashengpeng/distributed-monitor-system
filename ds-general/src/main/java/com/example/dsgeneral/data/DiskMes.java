package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class DiskMes {
    /**
     * 自增id
     */
    private Integer id;
    private String host;
    private String name;
    private String model;
    private long size;
    private long readed;
    private long writed;
    private String count;
}
