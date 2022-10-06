package com.softwaredesign;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pcap4j.core.PcapNetworkInterface;


class MainTest {

    @Test
    // Test if the getAllDevices() function returns a list of network interfaces
    void getAllDevicesTest() throws IOException {
        NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();
        final List<PcapNetworkInterface> allDevices = networkInterfaceHandler.getAllDevices();
        Assertions.assertTrue(allDevices.size() > 0);
    }
}