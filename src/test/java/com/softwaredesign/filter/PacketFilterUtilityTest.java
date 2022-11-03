package com.softwaredesign.filter;

import com.softwaredesign.filter.PacketFilterUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacketFilterUtilityTest {

    private PacketFilterUtility packetFilterUtility;

    @BeforeEach
    void setUp() {
        packetFilterUtility = new PacketFilterUtility();
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

}