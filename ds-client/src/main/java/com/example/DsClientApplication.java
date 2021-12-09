package com.example;

import com.example.dsclient.net.NettyClient.NettyClient;
import com.example.dsclient.net.QueryServer.QueryServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class DsClientApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DsClientApplication.class, args);

        QueryServer queryServer = applicationContext.getBean(QueryServer.class);

        queryServer.start();


    }
}
