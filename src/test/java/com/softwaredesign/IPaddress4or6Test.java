package com.softwaredesign;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}