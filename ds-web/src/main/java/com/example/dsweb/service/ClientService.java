package com.example.dsweb.service;

import com.example.dsgeneral.data.*;

import java.util.List;

public interface ClientService {
    int getTotalCount();
    List<VueTable> findAllDataByLimit(Integer currIndex, Integer pageSize);
    VueTable findTableDataByIp(String ip);
    Cpu findCpuByIp(String ip);
    Meo findMeoByIp(String ip);
    NetWork findNetByIp(String ip);
    List<DiskMes> findDiskMesByIp(String ip);
    List<FileMes> findFileMesByIp(String ip);
}
