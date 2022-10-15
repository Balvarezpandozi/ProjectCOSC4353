package com.softwaredesign;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NetworkInterfaceHandlerTest implements Samples {

    final NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();

    @Test
    void testGetAllDevicesToRetrieveAtLeastOneDevice() throws IOException {
        List<PcapNetworkInterface> devices = networkInterfaceHandler.getAllDevices();
        assertNotEquals(true, devices.isEmpty());
    }

    @Test
    void listenForPacketsOnDevice() throws IOException, NotOpenException, PcapNativeException {
        List<PcapNetworkInterface> devices = networkInterfaceHandler.getAllDevices();
        for (PcapNetworkInterface device : devices) {
            PcapHandle handle = networkInterfaceHandler.listenForPacketsOnDevice(device, SNAPSHOT_LENGTH, READ_TIME_OUT, MAX_PACKETS);
            assertTrue(handle.isOpen());
            handle.breakLoop();
            handle.close();
            assertFalse(handle.isOpen());
        }
    }
}