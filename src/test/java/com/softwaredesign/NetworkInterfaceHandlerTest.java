package com.softwaredesign;

import org.junit.jupiter.api.Test;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;
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

    @Test
    void listenForInfinitePacketsOnDevice() throws IOException, NotOpenException, PcapNativeException, InterruptedException {
        PcapNetworkInterface device = networkInterfaceHandler.getAllDevices().get(2);

        PcapHandle handle = networkInterfaceHandler.listenForPacketsOnDevice(device, READ_TIME_OUT);

        sleep(500);
        assertTrue(handle.isOpen());
        handle.breakLoop();
        handle.close();
        assertFalse(handle.isOpen());
    }
}
