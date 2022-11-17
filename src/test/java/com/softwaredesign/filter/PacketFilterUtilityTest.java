package com.softwaredesign.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PacketFilterUtilityTest {

    private PacketFilterUtility packetFilterUtility;
    private Scanner sc;

    @BeforeEach
    void setUp() {
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
    }

    @Test
    void testIsAffirmativeForNullInput() {
        String input = null;
        Assertions.assertFalse(packetFilterUtility.isAffirmative(input));
    }

    @Test
    void testIsAffirmativeForEmptyInput() {
        String input = "";
        Assertions.assertFalse(packetFilterUtility.isAffirmative(input));
    }

    @Test
    void testIsAffirmativeForInput_N() {
        String input = "n";
        Assertions.assertFalse(packetFilterUtility.isAffirmative(input));
        input = "N";
        Assertions.assertFalse(packetFilterUtility.isAffirmative(input));
        input = "No";
        Assertions.assertFalse(packetFilterUtility.isAffirmative(input));
        input = "NO";
        Assertions.assertFalse(packetFilterUtility.isAffirmative(input));
        input = "no";
        Assertions.assertFalse(packetFilterUtility.isAffirmative(input));
    }

    @Test
    void testIsAffirmativeForInput_Y() {
        String input = "y";
        Assertions.assertTrue(packetFilterUtility.isAffirmative(input));
        input = "Y";
        Assertions.assertTrue(packetFilterUtility.isAffirmative(input));
        input = "Yes";
        Assertions.assertTrue(packetFilterUtility.isAffirmative(input));
        input = "YES";
        Assertions.assertTrue(packetFilterUtility.isAffirmative(input));
        input = "yes";
        Assertions.assertTrue(packetFilterUtility.isAffirmative(input));
    }

    @Test
    void testCreateFilter_ip6() {
        List<String> inputs = new ArrayList<>();
        inputs.add("y");
        inputs.add("ip6");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("ip6",filter);
    }

    @Test
    void testCreateFilter_ip4() {
        List<String> inputs = new ArrayList<>();
        inputs.add("y");
        inputs.add("ip");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("ip",filter);
    }

    @Test
    void testCreateFilter_Protocol_Dir_Host() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("y");
        inputs.add("tcp");
        inputs.add("y");
        inputs.add("src");
        inputs.add("y");
        inputs.add("10.101.160.90");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("tcp src host 10.101.160.90",filter);
    }

    @Test
    void testCreateFilter_NoProtocol_Dir_Host() {
        String input = "n\rn\ry\rsrc\ry\r10.101.160.90\r";
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("src");
        inputs.add("y");
        inputs.add("10.101.160.90");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("src host 10.101.160.90",filter);
    }

    @Test
    void testCreateFilter_Protocol_NoDir_Host() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("y");
        inputs.add("tcp");
        inputs.add("n");
        inputs.add("y");
        inputs.add("10.101.160.90");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("tcp host 10.101.160.90",filter);
    }

    @Test
    void testCreateFilter_NoProtocol_NoDir_Host() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("10.101.160.90");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("host 10.101.160.90",filter);
    }

    @Test
    void testCreateFilter_Protocol_Dir_Port() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("y");
        inputs.add("tcp");
        inputs.add("y");
        inputs.add("src");
        inputs.add("n");
        inputs.add("y");
        inputs.add("8010");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("tcp src port 8010",filter);
    }

    @Test
    void testCreateFilter_NoProtocol_Dir_Port() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("src");
        inputs.add("n");
        inputs.add("y");
        inputs.add("8010");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("src port 8010",filter);
    }

    @Test
    void testCreateFilter_Protocol_NoDir_Port() {
        String input = "n\ry\rtcp\rn\rn\ry\r8010\r";
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("y");
        inputs.add("tcp");
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("8010");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("tcp port 8010",filter);
    }

    @Test
    void testCreateFilter_NoProtocol_NoDir_Port() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("n");
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("8010");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("port 8010",filter);
    }

    @Test
    void testCreateFilter_Protocol_Dir_PortRange() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("y");
        inputs.add("tcp");
        inputs.add("y");
        inputs.add("src");
        inputs.add("n");
        inputs.add("y");
        inputs.add("1000-5000");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("tcp src portrange 1000-5000",filter);
    }

    @Test
    void testCreateFilter_NoProtocol_Dir_PortRange() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("src");
        inputs.add("n");
        inputs.add("y");
        inputs.add("1000 -5000");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("src portrange 1000-5000",filter);
    }

    @Test
    void testCreateFilter_Protocol_NoDir_PortRange() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("y");
        inputs.add("tcp");
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("1000- 5000");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("tcp portrange 1000-5000",filter);
    }

    @Test
    void testCreateFilter_NoProtocol_NoDir_PortRange() {
        List<String> inputs = new ArrayList<>();
        inputs.add("n");
        inputs.add("n");
        inputs.add("n");
        inputs.add("n");
        inputs.add("y");
        inputs.add("1000 - 5000");
        String inputStream = String. join("\r", inputs);
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        System.setIn(in);
        packetFilterUtility = new PacketFilterUtility(new Scanner(System.in));
        String filter = packetFilterUtility.createFilter();
        Assertions.assertNotNull(filter);
        Assertions.assertEquals("portrange 1000-5000",filter);
    }
}