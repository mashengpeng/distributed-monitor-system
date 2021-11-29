package com.example.dsregister.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Gjing
 *
 * netty服务初始化器
 **/
@Component
public class RegistChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    NettyRegistHandler nettyRegistHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //添加编解码
        socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
//        socketChannel.pipeline().addLast(new ReadTimeoutHandler(60));
        socketChannel.pipeline().addLast(nettyRegistHandler);
    }
}