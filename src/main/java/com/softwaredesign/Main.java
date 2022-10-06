package com.softwaredesign;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.*;
import java.io.*;;

public class Main {
    public static void main(String[] args) {

        
        // Create a new NetworkInterfaceHandler object
        NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();
        Scanner kbr = new Scanner(System.in);
        System.out.print("Write a command: ");
        String getPacket= kbr.next();
        while(getPacket.equals("getPacket")){
            try{
                List<PcapNetworkInterface> allDevices = networkInterfaceHandler.getAllDevices();
                System.out.println("All devices: " + allDevices);
                PcapNetworkInterface device = allDevices.get(1);
                System.out.println("Device: " + device);
                PcapHandle handle = networkInterfaceHandler.listenForPacketsOnDevice(device, 65536, 10, 10);
                System.out.println("Handle: " + handle);
                List<Packet> packets = networkInterfaceHandler.getPackets();
                System.out.println("Packets: " + packets);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PcapNativeException e) {
                e.printStackTrace();
            } catch (NotOpenException e) {
                e.printStackTrace();
            }

            for (Packet packet : networkInterfaceHandler.getPackets()) {
                    System.out.println(packet);
                }
            System.out.println("Amount of packets: " + networkInterfaceHandler.getPackets().size());
            
        }
      
        
    }
}