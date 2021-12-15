package com.example.dsregister.net;

import com.alibaba.fastjson.JSON;

import com.example.dsgeneral.data.Carrier;
import com.example.dsregister.ServerAddress;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author Administrator
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyRegistHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客户端连接会触发
     */

    @Autowired
    ServerAddress serverAddress;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("接受了一个连接:" + ctx.channel().hashCode());
    }
    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Carrier carrier = JSON.parseObject((String) msg, Carrier.class);
        if(carrier.info.containsKey("server address")){
            InetSocketAddress inetSocketAddress = JSON.parseObject(carrier.info.get("server address").toString(), InetSocketAddress.class);
            serverAddress.addAddress(ctx.channel().hashCode(), inetSocketAddress);
            ctx.writeAndFlush("服务器地址已添加");
            log.info("添加了一个server地址，地址为："+inetSocketAddress.toString());
        }else if(carrier.info.containsKey("Which server should i report to?")){
            ctx.writeAndFlush(JSON.toJSONString(serverAddress.getAddress()));
            log.info("向client返回了一个server地址，地址为："+serverAddress.getAddress());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        serverAddress.removeAddress(ctx.channel().hashCode());
        log.info("关闭了一个连接:" + ctx.channel().hashCode());
    }

    /**
     * 发生异常触发
     */



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}