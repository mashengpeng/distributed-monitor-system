package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class VueTable {
    private Long id;
    private String host;
    private String time;
    private String osName;
    /**
     * Mac地址
     */
    private String macaddr;
    /**
     * 用户态使用率
     */
    private String userper;
    /**
     * 内核态使用率
     */
    private String sysper;
    /**
     * 物理内存空闲率
     */
    private String phyAvailable;
    /**
     * 虚拟内存使用率
     */
    private String virInuse;
    /**
     * 接受字节
     */
    private long bytesRecv;
    /**
     * 发送字节
     */
    private long bytesSent;
    /**
     * 速度 /千兆/百兆
     */
    private long speed;
}
