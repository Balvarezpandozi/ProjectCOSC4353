package com.softwaredesign;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;

public class PcapFileWriterAndReader {
    public void writePacketsToFile(String fileName, ArrayList<Packet> packets, PcapHandle handle) throws NotOpenException, PcapNativeException {
        PcapDumper dumper = handle.dumpOpen(fileName + ".pcap");

        for (Packet packet: packets) {
            try {
                dumper.dump(packet, handle.getTimestamp());
            } catch (NotOpenException e) {
                e.printStackTrace();
            }
        }

        dumper.close();
    }
}
