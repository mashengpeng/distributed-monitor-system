package com.example.dsgeneral;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DsGeneralApplicationTests.class })//这里加启动类

class DsGeneralApplicationTests {

    @Test
    void contextLoads() throws UnknownHostException {

        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr = InetAddress.getLocalHost();

        String ip = addr.getHostAddress();
        System.out.println(ip);
        System.out.println(addr.getHostName());
        System.out.println(r.availableProcessors());
        System.out.println(r.freeMemory());
        System.out.println(r.maxMemory());
        System.out.println(props.getProperty("os.name"));
        System.out.println(props.getProperty("os.arch"));
        System.out.println(props.getProperty("os.version"));


    }

}
