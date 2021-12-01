package com.example.dsweb.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket")
@Component
@Slf4j
public class WebSocketServer {

    private static ConcurrentHashMap<Session,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;



    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketMap.put(session, this);
        log.info("websocket接受了一个连接");
    }

    @OnClose
    public void onClose(){
        webSocketMap.remove(session);
        log.info("websocket断开了一个连接");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("websocket收到信息：{}",message);
    }

    public static void sendMessage(String message) throws IOException {
        log.info("websocket发送通知到浏览器");
        for(Session s : webSocketMap.keySet()){
            s.getBasicRemote().sendText(message);
        }
    }


}
