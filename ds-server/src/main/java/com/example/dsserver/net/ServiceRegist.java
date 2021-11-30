package com.example.dsserver.net;

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
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
@Component
public class ServiceRegist {

    public void start(int port){
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
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter());
                        }
                    });
            log.info("连接到注册中心");
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 20000));
            Thread.sleep(3 * 1000);

            Carrier carrier = new Carrier();
            carrier.info.put("server address", new InetSocketAddress("127.0.0.1", port));
            log.info("发送本地地址到注册中心");
            channelFuture.channel().writeAndFlush(JSON.toJSONString(carrier));
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
