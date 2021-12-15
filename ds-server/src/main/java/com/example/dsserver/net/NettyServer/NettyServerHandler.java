package com.example.dsserver.net.NettyServer;

import com.alibaba.fastjson.JSON;
import com.example.dsgeneral.data.OfflineMes;
import com.example.dsserver.ClientsList;
import com.example.dsserver.dao.OfflineClientDao;
import com.example.dsserver.service.impl.ClientServiceImpl;
import com.example.dsgeneral.data.SumData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    ClientsList clientsList;

    @Autowired
    OfflineClientDao offlineClientDao;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("接受到一个client连接");
        String message = "有新client上线了";
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                restTemplate.postForEntity("http://localhost:80/wsapi", message, String.class);
            }
        });

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                SumData sumData = clientsList.m.get(ctx.channel().hashCode());
                OfflineMes offlineMes = new OfflineMes();
                offlineMes.setHost(sumData.getClientMes().getHost());
                offlineMes.setLastOnlineTime(sumData.getClientMes().getTime());
                offlineMes.setOsName(sumData.getClientMes().getOsName());
                offlineClientDao.insertOrUpdateOfflineMes(offlineMes);

                String message = "client:" + sumData.getClientMes().getHost() + "下线了";
                restTemplate.postForEntity("http://localhost:80/wsapi", message, String.class);
            }
        });


    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        SumData sumData = JSON.parseObject((String)msg, SumData.class);
        log.info("server收到" + sumData.getClientMes().getHost() + "发送的报告");

        final EventLoop loop = ctx.channel().eventLoop();
        loop.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                clientService.insertOrUpdateClientMsg(sumData.getClientMes());
                clientService.insertOrUpdateCpu(sumData.getCpu());
                clientService.insertOrUpdateDiskMsg(sumData.getDiskMes());
                clientService.insertOrUpdateFileMsg(sumData.getFileMes());
                clientService.insertOrUpdateMeo(sumData.getMeo());
                clientService.insertOrUpdateNet(sumData.getNetWork());

                clientsList.m.put(ctx.channel().hashCode(), sumData);
                offlineClientDao.deleteByHost(sumData.getClientMes().getHost());
            }
        });



        ctx.writeAndFlush("已收到，消息处理channel的hashcode为：" + ctx.channel().hashCode());
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