package com.example.dsserver.net.ServiceRegist;

import com.alibaba.fastjson.JSON;
import com.example.dsgeneral.data.Carrier;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
@Component
public class ServiceRegist {

    int port;

    public void setPort(int port) {
        this.port = port;
    }

    @Autowired
    ServiceRegistHandler serviceRegistHandler;

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast(serviceRegistHandler);
                        }
                    });

            ChannelFuture channelFuture = bootstrap
                    .connect(new InetSocketAddress("127.0.0.1", 20000))
                    .addListener(new ConnectionListener(this))
                    .sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//
//            group.shutdownGracefully();
//        }
    }
}
