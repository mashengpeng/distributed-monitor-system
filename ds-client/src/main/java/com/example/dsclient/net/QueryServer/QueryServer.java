package com.example.dsclient.net.QueryServer;

import com.example.dsclient.net.NettyClient.NettyClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class QueryServer {

    @Autowired
    QueryServerHandler queryServerHandler;

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel p) throws Exception {
                        p.pipeline().addLast("decoder", new StringDecoder());
                        p.pipeline().addLast("encoder", new StringEncoder());
                        p.pipeline().addLast(queryServerHandler);
                    }
                });

        ChannelFuture connect = null;
        try {
            connect = bootstrap
                    .connect("127.0.0.1", 20000)
                    .addListener(new ConnectionListener(this))// netty 启动时如果连接失败，会断线重连
                    .sync();
            // 关闭客户端
            connect.channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(5000);
            group.shutdownGracefully();
        }


    }
}