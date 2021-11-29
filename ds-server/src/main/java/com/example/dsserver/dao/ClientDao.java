package com.example.dsserver.dao;

import com.example.dsgeneral.data.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClientDao {
    boolean insertOrUpdateClientMsg(ClientMes clientMes);
    boolean insertOrUpdateCpu(Cpu cpuMsg);
    boolean insertOrUpdateMeo(Meo meo);
    boolean insertOrUpdateNet(NetWork netWork);
    boolean insertOrUpdateDiskMsg(List<DiskMes> diskMes);
    boolean insertOrUpdateFileMsg(List<FileMes> fileMes);
//    boolean deleteAll();
}