package com.example.dsserver;

import com.example.dsserver.net.NettyServer;
import com.example.dsserver.net.ServiceRegist;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DsServerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DsServerApplication.class, args);

        int port = (int) (Math.random()*10000) + 10001;
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                NettyServer nettyServer = applicationContext.getBean(NettyServer.class);
                nettyServer.start(port);
            }
        }, 0, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                ServiceRegist serviceRegist = applicationContext.getBean(ServiceRegist.class);
                serviceRegist.start(port);
            }
        }, 0, TimeUnit.SECONDS);
    }

}