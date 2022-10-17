package com.softwaredesign;

import org.apache.commons.lang3.time.StopWatch;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();
        try {
            sniff(networkInterfaceHandler,2, 5000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sniff(NetworkInterfaceHandler networkInterfaceHandler, int deviceIndex, int timeLimit) throws IOException, PcapNativeException, NotOpenException, InterruptedException {
        PcapNetworkInterface device = networkInterfaceHandler.getAllDevices().get(deviceIndex);
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        PcapHandle handle = networkInterfaceHandler.listenForPacketsOnDevice(device,timeLimit);
        sleep(timeLimit);
        handle.breakLoop();
        handle.close();
        stopwatch.stop();
        System.out.println("Time Elapsed: " + stopwatch.getTime());
    }
}