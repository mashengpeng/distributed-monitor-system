package com.example.dsclient.net;

import com.alibaba.fastjson.JSON;
import com.example.dsclient.utils.OshiUtil;
import com.example.dsgeneral.data.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Administrator
 */
@Slf4j

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接到服务器");
        TimerTask timerTask = new TimerTask() {
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
                    log.info("已发送数据" + LocalDateTime.now());
                    ctx.writeAndFlush(JSON.toJSONString(sumData));
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 3000);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端收到消息: {}", msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}