package com.example.dsserver.dao;

import com.example.dsgeneral.data.OfflineMes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OfflineClientDao {

    boolean insertOrUpdateOfflineMes(OfflineMes offlineMes);

    boolean deleteByHost(String host);


}
