package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class NetWork {
    /**
     * 自增id
     */
    private Integer id;
    private String host;
    /**
     * Name
     */
    private String name;
    /**
     * Mac地址
     */
    private String macaddr;

    /**
     * 接受字节
     */
    private long bytesRecv;
    /**
     * 发送字节
     */
    private long bytesSent;
    /**
     * 收包
     */
    private long packetsRecv;
    /**
     * 发包
     */
    private long packetsSent;
    /**
     * 速度 /千兆/百兆
     */
    private long speed;
    /**
     * 丢包率
     */
    private long inDrops;


}
