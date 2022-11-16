package com.softwaredesign;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dir = System.getProperty("user.dir") + "\\src\\main\\java\\com\\softwaredesign\\";
        try{
            File file = new File(dir + "WelcomeFile.txt");
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNextLine()){
                String data = fileReader.nextLine();
                System.out.println(data);
            }
            fileReader.close();
        }catch(FileNotFoundException e){
            System.out.println("Program is not working!");
            e.printStackTrace();
            System.exit(0);
        }
        
        System.out.print("type the command you want to execute: ");

       
        String input = scanner.nextLine();
        // Create a new network interface handler
        NetworkInterfaceHandler networkInterfaceHandler = NetworkInterfaceHandler.getInstance();
        while(!input.equals("exit")){
            if(input.equals("-n get -s local")){
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
            }else if(input.equals("-h ping")){
                // ping host to get IP address and Mac address
                getHostsIpAndMac.getNetworkDevices();
                
           }else if(input.equals("help")){
                try{
                    File file = new File(dir + "HelpFile.txt");
                    Scanner fileReader = new Scanner(file);
                    while(fileReader.hasNextLine()){
                        String data = fileReader.nextLine();
                        System.out.println(data);
                    }
                    fileReader.close();
                }
                catch(FileNotFoundException e){
                    System.out.println("command not found");
                    e.printStackTrace();
                    
                }
            }else{
                System.out.println("Invalid command. Type 'help' to see the commands.");
            }
            System.out.print("type the new command you want to execute, to terminate program type 'exit': ");
            input = scanner.nextLine();

           }

        }
    }

