package com.example.dsweb.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.InetSocketAddress;

@Controller
@Slf4j
@ResponseBody
public class WebSocketController {


    @PostMapping("/wsapi")
    public void sendOnlineMessage(@RequestBody String message) throws IOException {
        log.info("web服务器收到消息：{}", message);
        WebSocketServer.sendMessage(message);
    }


}
