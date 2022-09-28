package com.softwaredesign;

import org.junit.jupiter.api.Test;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NetworkInterfaceHandlerTest {

    final NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();

    @Test
    void testGetAllDevicesToRetrieveAtLeastOneDevice() throws IOException {
        List<PcapNetworkInterface> devices = networkInterfaceHandler.getAllDevices();
        assertNotEquals(true, devices.isEmpty());
    }
}