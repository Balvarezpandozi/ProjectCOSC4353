package com.softwaredesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PcapFileWriterAndReaderTest implements Samples{

    final NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();
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