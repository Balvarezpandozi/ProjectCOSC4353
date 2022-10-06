package com.softwaredesign;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

public class NetworkInterfaceHandler {
    private ArrayList<Packet> packets;

    /**
     * getAllDevices finds all network interfaces and returns them in a list
     * @return List of all network interfaces
     * @throws IOException
     */
    public List<PcapNetworkInterface> getAllDevices() throws IOException {
        List<PcapNetworkInterface> allDevices = null;
        try {
            allDevices = Pcaps.findAllDevs();
        } catch (PcapNativeException e) {
            throw new IOException(e.getMessage());
        }
        if (allDevices == null || allDevices.isEmpty()) {
            throw new IOException("No network interface to capture.");
        }
        return allDevices;
    }

    /**
     * listenForPacketsOnDevice creates a packet listener on the given network device and saves all packets on the class
     * packets list. It also initializes the packets list every ime it runs.
     * @param device: Network device to listen packets from
     * @param snapshotLength: How many bytes will the snapshot take
     * @param readTimeout: If the network device does not receive any data it will allow idle time to be the readTimeout variable(milliseconds)
     * @param maxPackets: Maximum amount of packets to capture from the network interface
     * @throws PcapNativeException
     */
    public PcapHandle listenForPacketsOnDevice(PcapNetworkInterface device, int snapshotLength, int readTimeout, int maxPackets) throws PcapNativeException, NotOpenException {
        final PcapHandle handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout); // Open the network interface
        packets = new ArrayList<>(); // Initialize the packets list

        PacketListener listener = new PacketListener() { // Create a packet listener
            @Override
            public void gotPacket(Packet packet) {
                // Override the default gotPacket() function and process packet
                packets.add(packet);
            }
        };

        try { // Try to capture packets
            handle.loop(maxPackets, listener);
            handle.breakLoop();
        } catch (InterruptedException | NotOpenException e) { // If the capture fails, throw an exception
            System.out.println(e);
            e.printStackTrace();
        }

        return handle;
    }

    /**
     * This function allows overloading for the previous listenForPacketsOnDevice function. Sets up default parameters.
     * @param device
     * @throws PcapNativeException
     */
    public PcapHandle listenForPacketsOnDevice(PcapNetworkInterface device) throws PcapNativeException, NotOpenException {
        int snapshotLength = 65536; // in bytes
        int readTimeout = 5; // in milliseconds 
        int maxPackets = 5; // Maximum amount of packets to capture
        return listenForPacketsOnDevice(device, snapshotLength, readTimeout, maxPackets);
    }

    /**
     * Prints all packets on the packets list
     */
    public void printAllPackets() {
        for (Packet packet: packets) {
            System.out.println("=====================================");
            System.out.println(packet);
            System.out.println("=====================================");
        }
    }

    /**
     * @return all packets as an ArrayList
     */
    public ArrayList<Packet> getPackets() {
        return packets;
    }
}
