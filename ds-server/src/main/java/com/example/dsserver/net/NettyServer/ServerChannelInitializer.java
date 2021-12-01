package com.example.dsserver.net.NettyServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Gjing
 *
 * netty服务初始化器
 **/
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //添加编解码
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        p.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        p.addLast(nettyServerHandler);
    }
}