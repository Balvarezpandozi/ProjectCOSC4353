package com.softwaredesign;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.IpV6Packet;
import org.pcap4j.packet.Packet;

public class IPaddress4or6Test {

    @Test
    void testIfAddressIsIPv4() {
        String ipv4Addr = "10.101.160.90";
        boolean result = IPaddress4or6.isIPv4(ipv4Addr);
        Assertions.assertTrue(result);
    }

    @Test
    void testIfAddressIsIPv6() {
        String ipv6Addr = "2001:db8:3333:4444:5555:6666:7777:8888";
        boolean result = IPaddress4or6.isIPv6(ipv6Addr);
        Assertions.assertTrue(result);
    }

    @Test
    void testIfPacketIsUsingIPv4() throws IllegalRawDataException {
        Packet ipv4 = getDummyIPv4Packet();

        boolean isIPv4 = IPaddress4or6.isPacketUsingIPv4(ipv4);
        boolean isIPv6 = IPaddress4or6.isPacketUsingIPv6(ipv4);
        Assertions.assertTrue(isIPv4);
        Assertions.assertFalse(isIPv6);
    }

    @Test
    void testIfPacketIsUsingIPv6() throws IllegalRawDataException {
        Packet ipv6 = getDummyIPv6Packet();

        boolean isIPv4 = IPaddress4or6.isPacketUsingIPv4(ipv6);
        boolean isIPv6 = IPaddress4or6.isPacketUsingIPv6(ipv6);
        Assertions.assertFalse(isIPv4);
        Assertions.assertTrue(isIPv6);
    }

    private Packet getDummyIPv4Packet() throws IllegalRawDataException {
        String dummyData = "e0:4f:d0:20:ea:3a:69:10:a2:d8:08:00:2b:30:30:9d";
        byte[] data = dummyData.getBytes();
        Packet ipv4 = IpV4Packet.newPacket(data,0,20);

        return ipv4;
    }

    private Packet getDummyIPv6Packet() throws IllegalRawDataException {
        String dummyData = "e0:4f:d0:20:ea:3a:69:10:a2:d8:08:00:2b:30:30:9d";
        byte[] data = dummyData.getBytes();
        Packet ipv6 = IpV6Packet.newPacket(data,0,40);

        return ipv6;
    }
}