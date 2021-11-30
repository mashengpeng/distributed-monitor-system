package com.example.dsgeneral.data;

import lombok.Data;

@Data
public class OfflineMes {

    int id;
    String lastOnlineTime;
    String host;
    String osName;
}
