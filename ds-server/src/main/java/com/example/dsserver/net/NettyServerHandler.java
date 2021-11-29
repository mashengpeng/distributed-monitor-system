package com.example.dsserver.net;

import com.alibaba.fastjson.JSON;
import com.example.dsserver.service.impl.ClientServiceImpl;
import com.example.dsgeneral.data.SumData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客户端连接会触发
     */
    @Autowired
    ClientServiceImpl clientService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("接受到一个客户端连接");

    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务器收到消息: " + msg);
        SumData sumData = JSON.parseObject((String)msg, SumData.class);
        clientService.insertOrUpdateClientMsg(sumData.getClientMes());
        clientService.insertOrUpdateCpu(sumData.getCpu());
        clientService.insertOrUpdateDiskMsg(sumData.getDiskMes());
        clientService.insertOrUpdateFileMsg(sumData.getFileMes());
        clientService.insertOrUpdateMeo(sumData.getMeo());
        clientService.insertOrUpdateNet(sumData.getNetWork());
        ctx.writeAndFlush("已收到" + ctx.channel().hashCode());
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