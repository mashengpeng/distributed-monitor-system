package com.example.dsweb.dao;

import com.example.dsgeneral.data.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClientDao {
    int getTotalCount();
    List<VueTable> findAllDataByLimit(Integer currIndex, Integer pageSize);
    VueTable findTableDataByIp(String ip);
    Cpu findCpuByIp(String ip);
    Meo findMeoByIp(String ip);
    NetWork findNetByIp(String ip);
    List<DiskMes> findDiskMesByIp(String ip);
    List<FileMes> findFileMesByIp(String ip);
}
