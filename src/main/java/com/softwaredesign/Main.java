package com.softwaredesign;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to the network analyzer!");
        System.out.println("Project was created by Diogo Aires, Atharva Rajurkar, Bryan Alvarez.");
        System.out.println("This program will analyze the network traffic on your computer and display the results.");
        System.out.println("To know the commands for the functionalities the project offers, type 'help'");
        System.out.println("To exit the program, type 'exit'");
        System.out.println();
        System.out.print("type the command you want to execute: ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // Create a new network interface handler
        NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();
        while(!input.equals("exit")){
            if(input.equals("-n get")){
                // Get all network interfaces
                List<PcapNetworkInterface> allDevices = null;
                try {
                    allDevices = networkInterfaceHandler.getAllDevices();
                    System.out.println("All devices: " + allDevices);
                    System.out.println();
                    System.out.println("Device: " + allDevices);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else if(input.equals("-n listen")){
                // Listen for packets on a specific network interface assigned by user
                System.out.println("Please enter the network interface you want to listen to: ");
                int deviceNum = scanner.nextInt();
                System.out.println("Please enter the snapshot length: ");
                int snapshotLength = scanner.nextInt();
                System.out.println("Please enter the read timeout: ");
                int readTimeout = scanner.nextInt();
                System.out.println("Please enter the maximum amount of packets to capture: ");
                int maxPackets = scanner.nextInt();
                try {
                    PcapNetworkInterface device = networkInterfaceHandler.getAllDevices().get(deviceNum);
                    PcapHandle handle = networkInterfaceHandler.listenForPacketsOnDevice(device, snapshotLength, readTimeout, maxPackets);
                    System.out.println("Listening for packets on " + deviceNum);
                    //List<Packet> packets = networkInterfaceHandler.getPackets();
                    System.out.println("'stop' to stop listening for packets");
                    scanner.nextLine();
                    if(scanner.nextLine().equals("stop")){
                        handle.breakLoop();
                    }
                    handle.close();
                    System.out.println("Stopped listening for packets on " + deviceNum);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (PcapNativeException e) {
                    System.out.println(e.getMessage());
                } catch (NotOpenException e) {
                    System.out.println(e.getMessage());
                }
                for (Packet packet : networkInterfaceHandler.getPackets()) {
                    System.out.println(packet);
                }
            } else if(input.equals("-g hosts")){
                // Get all hosts
                getHostsAddy.getNetworkDevices();
           }else{
                System.out.println("Invalid command. Type 'help' to see the commands.");
            }
            System.out.print("type the new command you want to execute, to terminate program type 'exit': ");
            input = scanner.nextLine();

           }

        }
    }

