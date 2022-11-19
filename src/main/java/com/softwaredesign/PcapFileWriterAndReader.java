package com.softwaredesign;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class PcapFileWriterAndReader {
    public void writePacketsToFile(String filePath, String fileName, ArrayList<Packet> packets, PcapHandle handle) throws NotOpenException, PcapNativeException {
        PcapDumper dumper = handle.dumpOpen(filePath + fileName + ".pcap");

        for (Packet packet: packets) {
            try {
                dumper.dump(packet, handle.getTimestamp());
            } catch (NotOpenException e) {
                e.printStackTrace();
            }
//            catch (NullPointerException e) {
//                System.out.println("No packets to write!");
//            }
        }

        dumper.close();
    }

    public ArrayList<Packet> readPacketsFromFile(String fileName) throws PcapNativeException, NotOpenException {
        PcapHandle handle = Pcaps.openOffline(fileName);
        Packet packet = handle.getNextPacket();
        ArrayList<Packet> packets = new ArrayList<>();
        packets.add(packet);
        while(packet != null) {
            packet = handle.getNextPacket();
            packets.add(packet);
        }
        return packets;
    }
}
