package com.softwaredesign;

import org.junit.jupiter.api.Test;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.Packet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PcapFileWriterAndReaderTest implements Samples{

    final NetworkInterfaceHandler networkInterfaceHandler = NetworkInterfaceHandler.getInstance();
    final PcapFileWriterAndReader pcapFileWriterAndReader = new PcapFileWriterAndReader();

    @Test
    void writePacketsToFile() throws PcapNativeException, IOException, NotOpenException {
        //Set up
        ArrayList<Packet> packets = new ArrayList<>();
        packets.add(SAMPLE_PACKET);
        PcapHandle handle = networkInterfaceHandler.getAllDevices().get(0).openLive(SNAPSHOT_LENGTH, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, MAX_PACKETS);
        try {
            pcapFileWriterAndReader.writePacketsToFile(FILE_PATH, DUMP_FILE_NAME, packets, handle);
        } catch (NullPointerException e) {
            System.out.println("No timestamp because test uses dummy handle.");
        }
        handle.close();

        File temp = new File(FILE_PATH + DUMP_FILE_NAME + ".pcap");
        assertTrue(temp.exists());
    }

    @Test
    void readPacketsFromFile() throws NotOpenException, PcapNativeException {
        List<Packet> packets = pcapFileWriterAndReader.readPacketsFromFile(FILE_PATH + DUMP_FILE_NAME + ".pcap");
        assertFalse(packets.isEmpty());
    }
}