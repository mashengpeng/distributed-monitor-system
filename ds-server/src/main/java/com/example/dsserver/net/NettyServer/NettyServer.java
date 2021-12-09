package com.example.dsserver.net.NettyServer;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author
 * <p>
 * 服务启动监听器
 **/
@Slf4j
@Component
public class NettyServer {

    @Autowired
    ServerChannelInitializer serverChannelInitializer;
    //new 一个主线程组
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    //new 一个工作线程组
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                //使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(port))
                //设置队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(serverChannelInitializer);

        //绑定端口,开始接收进来的连接
        try {
            ChannelFuture future = bootstrap.bind().sync();
            log.info("服务器启动开始,监听端口："+port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭主线程组
            bossGroup.shutdownGracefully();
            //关闭工作线程组
            workGroup.shutdownGracefully();
        }
    }
}