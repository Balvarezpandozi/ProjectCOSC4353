package com.softwaredesign;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;

//This class will contain sample inputs and outputs for the tests.
public interface Samples {
    int SNAPSHOT_LENGTH = 65536; // in Bytes
    int READ_TIME_OUT = 10; // in milliseconds
    int MAX_PACKETS = 1;

    Packet SAMPLE_PACKET = new EthernetPacket.Builder()
            .dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
            .srcAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
            .type(EtherType.IPV4)
            .payloadBuilder(null)
            .paddingAtBuild(true).build();

    String DUMP_FILE_NAME = "SAMPLE_DUMP_FILE";
    String FILE_PATH = "./src/test/java/";
}
