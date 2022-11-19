package com.softwaredesign;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.LinkLayerAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import static java.lang.Thread.sleep;


public class Main {
    static NetworkInterfaceHandler networkInterfaceHandler = NetworkInterfaceHandler.getInstance();
    static PcapFileWriterAndReader writerAndReader = new PcapFileWriterAndReader();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //COMMANDS
        Pattern listenByTimeRegex = Pattern.compile("-n listen -t (-w )?[0-9]+ [0-9]+", Pattern.CASE_INSENSITIVE); //[0-9]* -> amount of time in seconds, , [0-9]* -> device index
        Pattern listenByAmountOfPacketsRegex = Pattern.compile("-n listen -p (-w )?[0-9]+ [0-9]+", Pattern.CASE_INSENSITIVE); //[0-9]* -> amount of packets, [0-9]* -> device index
        Pattern helpRegex = Pattern.compile("help", Pattern.CASE_INSENSITIVE);
        Pattern exitRegex = Pattern.compile("exit", Pattern.CASE_INSENSITIVE);
        Pattern getPacketsRegex = Pattern.compile("-n show packets", Pattern.CASE_INSENSITIVE);
        Pattern readPacketsFromFileRegex = Pattern.compile("read -p [0-9a-z]+\\.pcap", Pattern.CASE_INSENSITIVE); //[0-9a-z]* -> file name
        Pattern filterPacketsRegex = Pattern.compile("filter [a-z]*", Pattern.CASE_INSENSITIVE); //[a-z]* -> filter type
        Pattern pingRegex = Pattern.compile("-h ping", Pattern.CASE_INSENSITIVE);
        Pattern getNetworkDevicesRegex = Pattern.compile("-n get -s local", Pattern.CASE_INSENSITIVE);

        String dir = System.getProperty("user.dir") + "\\src\\main\\java\\com\\softwaredesign\\";
        File welcomeFile = new File(dir + "WelcomeFile.txt");
        StringBuilder welcome = new StringBuilder();
        File helpFile = new File(dir + "HelpFile.txt");
        StringBuilder help = new StringBuilder();

        try{
            Scanner fileReader = new Scanner(welcomeFile);
            while(fileReader.hasNextLine()){
                welcome.append(fileReader.nextLine()).append("\n");
            }
            fileReader.close();
            fileReader = new Scanner(helpFile);
            while(fileReader.hasNextLine()){
                help.append(fileReader.nextLine()).append("\n");
            }
            fileReader.close();
        }catch(FileNotFoundException e){
            System.out.println("Welcome message was not found!");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println(welcome);
        System.out.print("Type the command you want to execute: ");

       
        String input = scanner.nextLine();

        while(!input.matches(exitRegex.pattern())){

            if (input.matches(helpRegex.pattern())) {
                //help
                System.out.println(help);
            } else if (input.matches(getNetworkDevicesRegex.pattern())) {
                //-n get -s local
                getNetworkDevices();

            } else if (input.matches(getPacketsRegex.pattern())) {
                //-n show packets
                getPackets();

            } else if (input.matches(listenByTimeRegex.pattern())) {
                //-n listen -t TIME(seconds) DEVICE INDEX(int)
                //Get arguments
                String[] numbers = getNumbersFromString(input);

                //Parse arguments
                String seconds = numbers[0];
                int parsedSeconds = Integer.parseInt(seconds);
                String index = numbers[1];
                int parsedIndex = Integer.parseInt(index);
                Boolean write = false;
                if (input.contains(" -w ")) write = true;
                listenByTime(parsedSeconds, parsedIndex, write);

            } else if (input.matches(listenByAmountOfPacketsRegex.pattern())) {
                //-n listen -p PACKETS(amount) DEVICE INDEX(int)
                //Get arguments
                String[] numbers = getNumbersFromString(input);

                //Parse arguments
                String packetAmount = numbers[0];
                int parsedPacketAmount = Integer.parseInt(packetAmount);
                String index = numbers[1];
                int parsedIndex = Integer.parseInt(index);
                Boolean write = false;
                if (input.contains(" -w ")) write = true;
                try {
                    listenByAmountOfPackets(parsedPacketAmount, parsedIndex, write);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } else if (input.matches(readPacketsFromFileRegex.pattern())) {
                // read -p FILE NAME(string)
                String fileName = getFileName(input);
                readPacketsFromFile(fileName);

            } else if (input.matches(filterPacketsRegex.pattern())) {
                // filter -p FILTER TYPE(string)\

            } else if (input.matches(pingRegex.pattern())) {
                //Ping host to get IP and MAC addresses
                getHostsIpAndMac.getNetworkDevices();

            } else {
                System.out.println("Command not found. Type \"help\" to see possible commands.");
            }

            System.out.print("type the new command you want to execute, to terminate program type 'exit': ");
            input = scanner.nextLine();
        }

        System.out.println("See you later!");

    }

    static void getNetworkDevices() {
        List<PcapNetworkInterface> allDevices = getDevices();

        System.out.println("Found " + allDevices.size() + " devices.\n");
        for (int i = 0; i<allDevices.size(); i++) {
            System.out.println("Device " + i + ":");
            System.out.println("\tName: " + allDevices.get(i).getName());
            System.out.println("\tDescription: " + allDevices.get(i).getDescription());
            List<PcapAddress> addresses = allDevices.get(i).getAddresses();
            System.out.println("\tAddresses:");
            for (PcapAddress address : addresses) {
                System.out.println("\t\t" + address.getAddress());
                System.out.println("\t\t\tNetmask: " + address.getNetmask());
                System.out.println("\t\t\tBroadcast Address: " + address.getBroadcastAddress());
                System.out.println("\t\t\tDestination Address: " + address.getDestinationAddress());
            }
            List<LinkLayerAddress> linkLayerAddresses = allDevices.get(i).getLinkLayerAddresses();
            System.out.println("\tLink Layer Addresses:");
            for (LinkLayerAddress linkLayerAddress : linkLayerAddresses) {
                System.out.println("\t\t" + linkLayerAddress);
            }
            System.out.println();
        }
    }

    private static void getPackets() {
        ArrayList<Packet> packets = networkInterfaceHandler.getPackets();
        if (packets == null || packets.size() <= 0) {
            System.out.println("No packets to show.");
        } else {
            outputPackets(packets);
        }
    }

    private static void listenByTime(int seconds, int deviceIndex, Boolean write) {
        int milliseconds = seconds * 1000;
        PcapHandle handle = null;
        System.out.println("Listening for " + seconds + " seconds. Wait...");
        try {
            handle = networkInterfaceHandler.sniff(deviceIndex, milliseconds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        } catch (NotOpenException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done listening!");

        if(write) writePacketsToFile(handle);
    }

    private static void listenByAmountOfPackets(int amountOfPackets, int deviceIndex, Boolean write) throws InterruptedException {
        PcapHandle handle;

        try {
            List<PcapNetworkInterface> devices = networkInterfaceHandler.getAllDevices();
            if (devices.size() <= deviceIndex) {
                System.out.println("Device #" + deviceIndex + " does not exist!");
                return;
            }
            System.out.println("Listening for " + amountOfPackets + " packets. Wait...");
            handle = networkInterfaceHandler.listenForPacketsOnDevice(devices.get(deviceIndex), amountOfPackets, Integer.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotOpenException e) {
            throw new RuntimeException(e);
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        }
        while (networkInterfaceHandler.getPackets().size() < amountOfPackets) {
            sleep(1000);
        }

        System.out.println("Done listening!");

        if (write) writePacketsToFile(handle);

        handle.close();

    }

    private static void readPacketsFromFile(String fileName) {
        ArrayList<Packet> packets;
        try {
            packets = writerAndReader.readPacketsFromFile(System.getProperty("user.dir") + "\\" + fileName);
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        } catch (NotOpenException e) {
            throw new RuntimeException(e);
        }
        outputPackets(packets);
    }

    private static void writePacketsToFile(PcapHandle handle) {
        //Get file name
        Boolean isNameValid = false;
        Pattern alphanumericRegex = Pattern.compile("[a-zA-Z0-9]+");
        String fileName = "";
        while(!isNameValid) {
            System.out.println("Give an alphanumeric name for the file: ");
            fileName = scanner.nextLine();
            if (fileName.matches(alphanumericRegex.pattern())) isNameValid = true;
            if (!isNameValid) System.out.print("Only letters and numbers. ");
        }

        //Obtain packets to write
        ArrayList<Packet> packets = networkInterfaceHandler.getPackets();

        //Write file
        try {
            writerAndReader.writePacketsToFile("", fileName, packets, handle);
        } catch (NotOpenException e) {
            throw new RuntimeException(e);
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        }
    }

    static String[] getNumbersFromString(String str) {
        String[] numbers = str
                .replaceAll("[^0-9]+", " ")
                .trim()
                .split(" ");
        return numbers;
    }

    static String getFileName(String str) {
        String fileName = str
                .replaceAll("^((write -p)|(read -p))", " ")
                .trim();
        return fileName;
    }

    static void outputPackets(ArrayList<Packet> packets) {
        System.out.println("Total packets: " + packets.size());
        Packet packet;
        String packetPiece = "";
        for (int i = 0; i< packets.size(); i++) {
            System.out.println("Packet #" + (i+1));
            packet = packets.get(i);
            System.out.println("    HEADER");
            System.out.println("\tLength: "+packet.getHeader().length());
            packetPiece = String.join("\n\t", packet.getHeader().toString().split("\n"));
            System.out.println("\t" + packetPiece + "\n");
            System.out.println("    PAYLOAD");
            System.out.println("\tLength: " + packet.getPayload().length());
            packetPiece = String.join("\n\t", packet.getPayload().toString().split("\n"));
            System.out.println("\t" + packetPiece + "\n");
            System.out.println("        HEADER");
            System.out.println("\t\tLength: " + packet.getPayload().getHeader().length());
            packetPiece = String.join("\n\t\t", packet.getPayload().getHeader().toString().split("\n"));
            System.out.println("\t\t" + packetPiece);
        }
    }

    static List<PcapNetworkInterface> getDevices() {
        List<PcapNetworkInterface> allDevices = null;

        try {
            allDevices = networkInterfaceHandler.getAllDevices();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allDevices;
    }
}

