package com.softwaredesign;


import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();
        try {
            System.out.println(networkInterfaceHandler.getAllDevices().get(0));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}