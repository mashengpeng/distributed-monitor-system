package com.example.dsclient.utils;

import com.example.dsgeneral.data.*;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.hardware.CentralProcessor.TickType;
import oshi.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class OshiUtil {
    private static SystemInfo systemInfo = new SystemInfo();
    private static HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
    private static  Properties props = System.getProperties();
    private static InetAddress  addr;

    static {
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static String getHostIp(){
        if(addr!=null){
            return addr.getHostAddress();
        }
        return "";
    }
    public static ClientMes getClientMes(){
        ClientMes clientMes = new ClientMes();
        clientMes.setHost(getHostIp());
        clientMes.setOsName(props.getProperty("os.name"));
        return clientMes;
    }

    public static NetWork getNetMsg(){
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        List<NetworkIF> nets = hal.getNetworkIFs();
        NetWork netWork = new NetWork();
        for (NetworkIF net : nets) {
            if(net.getIPv4addr().length!=0){
                netWork.setHost(getHostIp());
                netWork.setName(net.getName());
                netWork.setMacaddr(net.getMacaddr());
                netWork.setBytesRecv(net.getBytesRecv());
                netWork.setBytesSent(net.getBytesSent());
                netWork.setPacketsRecv(net.getPacketsRecv());
                netWork.setPacketsSent(net.getPacketsSent());
                netWork.setSpeed(net.getSpeed());
                netWork.setInDrops(net.getInDrops());
                break;
            }
        }
        return netWork;
    }
    public static Cpu getCup(){
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        CentralProcessor centralProcessor = hal.getProcessor();
        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
        // Wait a second...
        Util.sleep(1000);
        long[] ticks = centralProcessor.getSystemCpuLoadTicks();
        //System.out.println("CPU, IOWait, and IRQ ticks @ 1 sec:" + Arrays.toString(ticks));
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
        double userper = 100d * user / totalCpu;
        double niceper = 100d * nice / totalCpu;
        double sysper = 100d * sys / totalCpu;
        double idleper = 100d * idle / totalCpu;
        double iowaitper = 100d * iowait / totalCpu;
        double irqper = 100d * irq / totalCpu;
        double softirqper = 100d * softirq / totalCpu;
        double stealper = 100d * steal / totalCpu;
        int logProCount = centralProcessor.getPhysicalProcessorCount();
        int phyProCount = centralProcessor.getPhysicalProcessorCount();
        DecimalFormat df = new DecimalFormat(".00");
        Cpu cpu = new Cpu();
        cpu.setHost(getHostIp());
        cpu.setUserper(df.format(userper));
        cpu.setNiceper(df.format(niceper));
        cpu.setSysper(df.format(sysper));
        cpu.setIdleper(df.format(idleper));
        cpu.setIowaitper(df.format(iowaitper));
        cpu.setIrqper(df.format(irqper));
        cpu.setSoftirqper(df.format(softirqper));
        cpu.setStealper(df.format(stealper));
        cpu.setLogProCount(logProCount);
        cpu.setPhyProCount(phyProCount);
        return cpu;
    }
    public static Meo getMemory(){
        GlobalMemory memory = hardwareAbstractionLayer.getMemory();
        Meo meo = new Meo();
        long available = memory.getAvailable();
        long total = memory.getTotal();
        long inuse = memory.getVirtualMemory().getVirtualInUse();
        System.out.println("inuse:"+inuse);
        long max = memory.getVirtualMemory().getVirtualMax();
        System.out.println("VirtualMax:"+max);
        DecimalFormat df = new DecimalFormat(".00");
        meo.setHost(getHostIp());
        meo.setAvailable(df.format(100d *available/total));
        meo.setVirInuse(df.format(100d *inuse/max));
        return meo;
    }
    public static List<DiskMes> getDiskMes(){
       List<HWDiskStore> diskStores = hardwareAbstractionLayer.getDiskStores();
       List<DiskMes> diskMesList = new ArrayList<>();
       int count = diskStores.size();
        for (HWDiskStore disk : diskStores) {
            DiskMes diskMes = new DiskMes();
            String name = disk.getName();
            String model = disk.getModel();
            long size = disk.getSize();
            long readed = disk.getReadBytes();
            long writed = disk.getWriteBytes();
            String ip = getHostIp();
            diskMes.setHost(ip);
            diskMes.setName(name);
            diskMes.setModel(model);
            diskMes.setSize(size);
            diskMes.setReaded(readed);
            diskMes.setWrited(writed);
            diskMes.setCount(ip+"_"+count--);
            diskMesList.add(diskMes);
        }
        return diskMesList;
    }
    public static List<FileMes> getSysFiles(){
        OperatingSystem os = systemInfo.getOperatingSystem();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        List<FileMes> fileMesList = new ArrayList<>();
        int count = fsArray.size();
        for (OSFileStore fs : fsArray)
        {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            String ip = getHostIp();
            FileMes sysFile = new FileMes();
            sysFile.setHost(ip);
            sysFile.setDirName(fs.getMount());
            sysFile.setFree(free);
            sysFile.setTotal(total);
            sysFile.setUsage(used);
            sysFile.setCount(ip+"_"+count--);
            fileMesList.add(sysFile);
        }
        return fileMesList;
    }
}
