package com.softwaredesign;

public class Main {
    public static void main(String[] args) {
        NetworkInterfaceHandler networkInterfaceHandler = new NetworkInterfaceHandler();
        try {
            networkInterfaceHandler.sniff(2, 10000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}