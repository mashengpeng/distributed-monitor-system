package com.example.dsclient.net.QueryServer;

import com.alibaba.fastjson.JSON;
import com.example.dsclient.net.NettyClient.NettyClient;
import com.example.dsgeneral.data.Carrier;
import io.netty.channel.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ChannelHandler.Sharable
public class QueryServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    QueryServer queryServer;

    @Autowired
    NettyClient nettyClient;

    public void query(ChannelHandlerContext ctx){
        Carrier carrier = new Carrier();
        carrier.info.put("Which server should i report to?", "1");
        ctx.writeAndFlush(JSON.toJSONString(carrier));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接到注册中心");
        Thread.sleep(1000);

        query(ctx);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        final EventLoop loop = ctx.channel().eventLoop();
        loop.schedule(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                queryServer.start();
            }
        }, 0L, TimeUnit.SECONDS);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress inetSocketAddress = JSON.parseObject((String) msg, InetSocketAddress.class);
        if(inetSocketAddress == null){
            log.info("未查到服务器地址，3s后继续查询");
            Thread.sleep(3000);
            query(ctx);
        }else{
            log.info("查询到服务器地址为：" + inetSocketAddress);
            final EventLoop loop = ctx.channel().eventLoop();
            loop.schedule(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    nettyClient.start(inetSocketAddress);
                }
            }, 0L, TimeUnit.SECONDS);

            Thread.sleep(3000);
            ctx.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
