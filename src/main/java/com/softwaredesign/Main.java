package com.softwaredesign;

public class Main {
    public static void main(String[] args) {
        
        
         //String Ipaddress ="2001:db8:a0b:12f0::1";
         String Ipaddress = "42.197.20.45";

         if(IPaddress4or6.isIPv4(Ipaddress)){
             System.out.println("The IP address is IPv4");
         }
         else if(IPaddress4or6.isIPv6(Ipaddress)){
             System.out.println("The IP address is IPv6");
         }
         else{
             System.out.println("The IP address is neither IPv4 nor IPv6");
         }

    }
}