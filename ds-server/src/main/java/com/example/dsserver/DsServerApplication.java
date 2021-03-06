package com.example.dsserver;

import com.example.dsserver.net.NettyServer.NettyServer;
import com.example.dsserver.net.ServiceRegist.ServiceRegist;
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

        int port = (int) (Math.random()*10000) + 50001;

//        ServiceRegist serviceRegist = applicationContext.getBean(ServiceRegist.class);
//        serviceRegist.setPort(port);
//        serviceRegist.start();
//
//        NettyServer nettyServer = applicationContext.getBean(NettyServer.class);
//        nettyServer.setPort(port);
//        nettyServer.start();



        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                NettyServer nettyServer = applicationContext.getBean(NettyServer.class);
                nettyServer.setPort(port);
                nettyServer.start();
            }
        }, 0, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                ServiceRegist serviceRegist = applicationContext.getBean(ServiceRegist.class);
                serviceRegist.setPort(port);
                serviceRegist.start();
            }
        }, 0, TimeUnit.SECONDS);
    }

}