package com.example.dsregister;

import com.example.dsregister.net.NettyRegist;
import com.example.dsregister.redis.RedisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DsRegisterApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(DsRegisterApplication.class, args);
        RedisService redisService = applicationContext.getBean(RedisService.class);
        redisService.deleteAll();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                NettyRegist nettyRegist = applicationContext.getBean(NettyRegist.class);
                nettyRegist.start();
            }
        }, 0, TimeUnit.SECONDS);

    }

}
