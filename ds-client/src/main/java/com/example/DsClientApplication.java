package com.example;

import com.example.dsclient.net.NettyClient.NettyClient;
import com.example.dsclient.net.QueryServer.QueryServer;
import com.example.dsclient.net.ServerPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DsClientApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DsClientApplication.class, args);

        QueryServer queryServer = applicationContext.getBean(QueryServer.class);
        NettyClient nettyClient = applicationContext.getBean(NettyClient.class);
        ServerPort serverPort = applicationContext.getBean(ServerPort.class);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                queryServer.start();
            }
        }, 0, TimeUnit.SECONDS);

        while(serverPort.getInetSocketAddress() == null){}

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                nettyClient.start();
            }
        }, 0, TimeUnit.SECONDS);

    }
}
