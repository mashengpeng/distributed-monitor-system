package com.example.dsweb.service.impl;

import com.example.dsgeneral.data.*;
import com.example.dsweb.dao.ClientDao;
import com.example.dsweb.service.ClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Resource
    private ClientDao clientDao;

    @Override
    public int getTotalCount() {
        return clientDao.getTotalCount();
    }

    @Override
    public List<VueTable> findAllDataByLimit(Integer currIndex, Integer pageSize) {
        return clientDao.findAllDataByLimit(currIndex,pageSize);
    }

    @Override
    public VueTable findTableDataByIp(String ip) {
        return clientDao.findTableDataByIp(ip);
    }

    @Override
    public Cpu findCpuByIp(String ip) {
        return clientDao.findCpuByIp(ip);
    }

    @Override
    public Meo findMeoByIp(String ip) {
        return clientDao.findMeoByIp(ip);
    }

    @Override
    public NetWork findNetByIp(String ip) {
        return clientDao.findNetByIp(ip);
    }

    @Override
    public List<DiskMes> findDiskMesByIp(String ip) {
        return clientDao.findDiskMesByIp(ip);
    }

    @Override
    public List<FileMes> findFileMesByIp(String ip) {
        return clientDao.findFileMesByIp(ip);
    }
}
