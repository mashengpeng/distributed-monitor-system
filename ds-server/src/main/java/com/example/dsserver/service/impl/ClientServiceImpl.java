package com.example.dsserver.service.impl;

import com.example.dsserver.dao.ClientDao;
import com.example.dsserver.service.ClientService;
import com.example.dsgeneral.data.*;
import com.example.dsgeneral.utils.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Resource
    ClientDao clientDao;

    @Override
    public boolean insertOrUpdateClientMsg(ClientMes clientMes) {
        clientMes.setTime(TimeUtils.getStringDate());
        return clientDao.insertOrUpdateClientMsg(clientMes);
    }

    @Override
    public boolean insertOrUpdateCpu(Cpu cpuMsg) {
        return clientDao.insertOrUpdateCpu(cpuMsg);
    }

    @Override
    public boolean insertOrUpdateMeo(Meo meo) {
        return clientDao.insertOrUpdateMeo(meo);
    }

    @Override
    public boolean insertOrUpdateNet(NetWork netWork) {
        return clientDao.insertOrUpdateNet(netWork);
    }

    @Override
    public boolean insertOrUpdateDiskMsg(List<DiskMes> diskMes) {
        return clientDao.insertOrUpdateDiskMsg(diskMes);
    }

    @Override
    public boolean insertOrUpdateFileMsg(List<FileMes> fileMes) {
        return clientDao.insertOrUpdateFileMsg(fileMes);
    }

//    @Override
//    public boolean deleteAll() {
//        return clientDao.deleteAll();
//    }
}
