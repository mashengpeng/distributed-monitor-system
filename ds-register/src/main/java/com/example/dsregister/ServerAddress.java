package com.example.dsregister;


import com.alibaba.fastjson.JSON;
import com.example.dsregister.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.*;

@Component
public class ServerAddress {

    @Autowired
    RedisService redisService;

    HashMap<Integer, InetSocketAddress> server = new LinkedHashMap<>();

    public InetSocketAddress getAddress(){
        return JSON.parseObject(redisService.getRandom(), InetSocketAddress.class);
    }
    public void addAddress(Integer hashcode, InetSocketAddress inetSocketAddress){
        redisService.add(String.valueOf(hashcode), JSON.toJSONString(inetSocketAddress));
    }
    public void removeAddress(Integer hashcode){
        redisService.delete(String.valueOf(hashcode));
    }

}
