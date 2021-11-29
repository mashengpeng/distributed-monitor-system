package com.example.dsgeneral.data;
import lombok.Data;

@Data
public class Cpu {
    /**
     * 自增id
     */
    private Integer id;
    private String host;
    /**
     * 物理CPU核数
     */
    private int phyProCount;
    /**
     * 逻辑CPU核数
     */
    private int logProCount;

    /**
     * 用户态使用率
     */
    private String userper;

    /**
     * 低优先级用户态使用率
     */
    private String niceper;

    /**
     * 内核态使用率
     */
    private String sysper;

    /**
     * 空闲率
     */
    private String idleper;

    /**
     * io等待率
     */
    private String iowaitper;

    /**
     * 硬中断率
     */
    private String irqper;

    /**
     * 软中断率
     */
    private String softirqper;
    /**
     * 虚拟机占用率
     */
    private String stealper;

}
