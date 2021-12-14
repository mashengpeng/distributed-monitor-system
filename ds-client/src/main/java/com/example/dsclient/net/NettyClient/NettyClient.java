package com.example.dsclient.net.NettyClient;

import com.example.dsclient.net.QueryServer.QueryServer;
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

import java.net.InetSocketAddress;


@Component
@Slf4j
public class NettyClient {

    private EventLoopGroup group = new NioEventLoopGroup();

    @Autowired
    NettyClientHandler nettyClientHandler;


    public void start(InetSocketAddress inetSocketAddress) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
//                .remoteAddress(inetSocketAddress)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel p) throws Exception {
                        p.pipeline().addLast("decoder", new StringDecoder());
                        p.pipeline().addLast("encoder", new StringEncoder());
                        p.pipeline().addLast(nettyClientHandler);
                    }
                });

        try {
            log.info("开始连接server");
            ChannelFuture future = bootstrap.connect(inetSocketAddress).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finally {
//            Thread.sleep(5000);
//            group.shutdownGracefully();
//        }
    }
}