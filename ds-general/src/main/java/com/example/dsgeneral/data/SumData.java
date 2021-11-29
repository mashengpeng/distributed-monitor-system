package com.example.dsgeneral.data;

import lombok.Data;

import java.util.List;

@Data
public class SumData {
    private ClientMes clientMes;
    private NetWork netWork;
    private Cpu cpu;
    private Meo meo;
    private List<DiskMes> diskMes;
    private List<FileMes> fileMes;
}
