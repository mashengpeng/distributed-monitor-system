package com.example.dsclient.net.QueryServer;

import com.alibaba.fastjson.JSON;
import com.example.dsclient.net.ServerPort;
import com.example.dsgeneral.data.Carrier;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
@Component
public class QueryServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    ServerPort serverPort;

    public void query(Channel channel){
        Carrier carrier = new Carrier();
        carrier.info.put("Which server should i report to?", "1");
        channel.writeAndFlush(JSON.toJSONString(carrier));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接到注册中心");
        Thread.sleep(3000);
        query(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress inetSocketAddress = JSON.parseObject((String) msg, InetSocketAddress.class);
//        if(inetSocketAddress == null){
//            query(ctx.channel());
//            log.info("未查到服务器地址，继续查询");
//        }else{
//            serverPort.setInetSocketAddress(inetSocketAddress);
//            log.info("查询到服务器地址为：" + inetSocketAddress.toString());
//        }
        serverPort.setInetSocketAddress(inetSocketAddress);
        log.info("查询到服务器地址为：" + inetSocketAddress.toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
