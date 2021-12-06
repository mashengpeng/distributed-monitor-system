package com.example.dsweb.dao;


import com.example.dsgeneral.data.OfflineMes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OfflineDao {

    int getOfflineCount();

    List<OfflineMes> findAllOfflineByLimit(Integer currIndex, Integer pageSize);

    OfflineMes findOfflineByIp(String ip);
}
