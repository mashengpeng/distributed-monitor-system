package com.example.dsclient.net.QueryServer;

import com.example.dsclient.net.NettyClient.NettyClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
public class ConnectionListener implements ChannelFutureListener {
    private final QueryServer queryServer;

    public ConnectionListener(QueryServer queryServer){
        this.queryServer = queryServer;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            log.warn("未连接到注册中心，3s后尝试重新连接");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    queryServer.start();
                }
            }, 3L, TimeUnit.SECONDS);
        }
    }

}