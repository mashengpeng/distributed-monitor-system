package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class FileMes {
    /**
     * 自增id
     */
    private Integer id;
    private String host;
    private String dirName;
    private long total;
    private long free;
    private long usage;
    private String count;
}
