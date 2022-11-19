package com.softwaredesign;

import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetworkInterfaceHandler {
    private ArrayList<Packet> packets;
    private Integer INFINITY = -1;
    private static NetworkInterfaceHandler singletonInstance = null;

    private NetworkInterfaceHandler(){};
    /**
     * makes NetworkInterfaceHandler singleton
     */
    public static NetworkInterfaceHandler getInstance(){
        if (Objects.isNull(singletonInstance)) {
            singletonInstance = new NetworkInterfaceHandler();
        }
        return singletonInstance;
    }

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
    public PcapHandle listenForPacketsOnDevice(PcapNetworkInterface device, int snapshotLength, int readTimeout, int maxPackets) throws PcapNativeException {
        final PcapHandle handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout);
        packets = new ArrayList<>();
        long currTime = System.currentTimeMillis();

        PacketListener listener = packet -> {
            if (System.currentTimeMillis() - currTime >= readTimeout) {
                try {
                    handle.breakLoop();
                } catch (NotOpenException e) {
                    throw new RuntimeException(e);
                }
            }
            packets.add(packet);
            if (packets.size()%10 == 0) {
                System.out.println("Packets gotten so far: " + packets.size());
            }
        };



        try {
            handle.loop(maxPackets, listener);
        } catch (Exception e) {

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
        int readTimeout = 50; // in milliseconds
        int maxPackets = 50;
        return listenForPacketsOnDevice(device, snapshotLength, readTimeout, maxPackets);
    }

    /**
     * This function allows overloading for the previous listenForPacketsOnDevice function with max packets limit set to INFINITY. Sets up default parameters.
     * @param device
     * @throws PcapNativeException
     */
    public PcapHandle listenForPacketsOnDevice(PcapNetworkInterface device, int readTimeout) throws PcapNativeException, NotOpenException {
        int snapshotLength = 65536; // in bytes
        int maxPackets = INFINITY;
        return listenForPacketsOnDevice(device, snapshotLength, readTimeout, maxPackets);
    }

    public PcapHandle listenForPacketsOnDevice(PcapNetworkInterface device, int maxPackets, int readTimeout) throws PcapNativeException, NotOpenException {
        int snapshotLength = 65536; // in bytes
        return listenForPacketsOnDevice(device, snapshotLength, readTimeout, maxPackets);
    }

    /**
     * @return all packets as an ArrayList
     */
    public ArrayList<Packet> getPackets() {
        return packets;
    }

    public PcapHandle sniff(int deviceIndex, int timeLimit) throws IOException, PcapNativeException, NotOpenException, InterruptedException {
        PcapHandle handle = null;
        List<PcapNetworkInterface> devices = getAllDevices();
        if (devices.size() > deviceIndex) {
            PcapNetworkInterface device = devices.get(deviceIndex);
            handle = listenForPacketsOnDevice(device,timeLimit);
            handle.breakLoop();
            handle.close();
        } else {
            System.out.println("That device does not exist!");
        }

        return handle;
    }
}
