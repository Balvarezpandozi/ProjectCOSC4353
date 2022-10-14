package com.softwaredesign;

import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.Packet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        final PcapHandle handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout);
        packets = new ArrayList<>();

        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                // Override the default gotPacket() function and process packet
                packets.add(packet);
            }
        };

        Thread packetLoop = new Thread(() -> {
            try {
                handle.loop(maxPackets, listener);
            } catch (Exception e) {
                System.out.println(e.getClass());
            }
        });

        packetLoop.start();

        return handle;
    }

    /**
     * This function allows overloading for the previous listenForPacketsOnDevice function. Sets up default parameters.
     * @param device
     * @throws PcapNativeException
     */
    public PcapHandle listenForPacketsOnDevice(PcapNetworkInterface device) throws PcapNativeException, NotOpenException {
        int snapshotLength = 65536; // in bytes
        int readTimeout = 50; // in milliseconds
        int maxPackets = 50;
        return listenForPacketsOnDevice(device, snapshotLength, readTimeout, maxPackets);
    }

    /**
     * @return all packets as an ArrayList
     */
    public ArrayList<Packet> getPackets() {
        return packets;
    }
}
