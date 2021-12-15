package com.example.dsserver.net.ServiceRegist;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
public class ConnectionListener implements ChannelFutureListener {
    private final ServiceRegist serviceRegist;

    public ConnectionListener(ServiceRegist serviceRegist){
        this.serviceRegist = serviceRegist;
    }


    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            log.warn("未连接到register，3s后尝试重新连接");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    serviceRegist.start();
                }
            }, 3L, TimeUnit.SECONDS);
        }
    }

}