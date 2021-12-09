package com.example.dsclient.net.NettyClient;

import com.alibaba.fastjson.JSON;
import com.example.dsclient.net.QueryServer.QueryServer;
import com.example.dsclient.utils.OshiUtil;
import com.example.dsgeneral.data.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;


/**
 * @author Administrator
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    @Lazy
    QueryServer queryServer;

    ScheduledExecutorService scheduledExecutorService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接到服务器");

        scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                NetWork net = OshiUtil.getNetMsg();
                Cpu cpu = OshiUtil.getCup();
                Meo meo = OshiUtil.getMemory();
                List<DiskMes> diskMes =  OshiUtil.getDiskMes();
                List<FileMes> fileMes = OshiUtil.getSysFiles();
                ClientMes clientMes = OshiUtil.getClientMes();
                SumData sumData = new SumData();
                sumData.setClientMes(clientMes);
                sumData.setCpu(cpu);
                sumData.setDiskMes(diskMes);
                sumData.setMeo(meo);
                sumData.setNetWork(net);
                sumData.setFileMes(fileMes);
                log.info("已发送数据，当前时间：" + LocalDateTime.now());
                ctx.writeAndFlush(JSON.toJSONString(sumData));
            }
        },1,5,TimeUnit.SECONDS);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端收到消息: {}", msg.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        scheduledExecutorService.shutdown();

        final EventLoop loop = ctx.channel().eventLoop();
        loop.schedule(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                queryServer.start();
            }
        }, 0L, TimeUnit.SECONDS);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}