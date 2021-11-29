package com.example.dsgeneral.data;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class Carrier {

    public HashMap info;

    public Carrier(){
        info = new HashMap<>();
    }

}
