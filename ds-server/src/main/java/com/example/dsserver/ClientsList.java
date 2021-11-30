package com.example.dsserver;

import com.example.dsgeneral.data.SumData;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component

public class ClientsList {

    public HashMap<Integer, SumData> m = new HashMap<>();
}
