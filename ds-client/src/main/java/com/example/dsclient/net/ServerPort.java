package com.example.dsclient.net;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Data
@Component
public class ServerPort {

    InetSocketAddress inetSocketAddress;

}
