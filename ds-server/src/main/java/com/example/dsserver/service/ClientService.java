package com.example.dsserver.service;

import com.example.dsgeneral.data.*;

import java.util.List;

public interface ClientService {

    boolean insertOrUpdateClientMsg(ClientMes clientMes);
    boolean insertOrUpdateCpu(Cpu cpuMsg);
    boolean insertOrUpdateMeo(Meo meo);
    boolean insertOrUpdateNet(NetWork netWork);
    boolean insertOrUpdateDiskMsg(List<DiskMes> diskMes);
    boolean insertOrUpdateFileMsg(List<FileMes> fileMes);
//    boolean deleteAll();

}
