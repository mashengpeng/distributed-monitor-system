package com.example.dsserver.net.ServiceRegist;


import com.alibaba.fastjson.JSON;
import com.example.dsgeneral.data.Carrier;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ServiceRegistHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    ServiceRegist serviceRegist;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接到注册中心");

        Thread.sleep(3000);
        Carrier carrier = new Carrier();
        carrier.info.put("server address", new InetSocketAddress("127.0.0.1", serviceRegist.port));
        ctx.writeAndFlush(JSON.toJSONString(carrier));

        log.info("发送本地地址到注册中心");

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
                serviceRegist.start();
            }
        }, 0L, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到消息: {}", msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
