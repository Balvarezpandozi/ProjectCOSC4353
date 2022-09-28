package com.softwaredesign;
import java.net.*;

// Checks whether the IP address is IPv4 or IPv6
//Main drawback for now is DNS check used by InetAddress constructor
public class IPaddress4or6 {

    public static boolean isIPv4(String IP_address){
        boolean isIPv4 = false; //default value
        try{
            InetAddress inet = InetAddress.getByName(IP_address); //DNS check
            isIPv4 = inet.getHostAddress().equals(IP_address);//check if IP address is IPv4
        }
        catch(UnknownHostException e){
            isIPv4 = false;
        }
        return isIPv4;
        
    }

    public static boolean isIPv6(String IP_address){
        
        boolean isIPv6 = false; //default value
        try{
            InetAddress inet = InetAddress.getByName(IP_address);//DNS check
            isIPv6 = inet.getHostAddress().equals(IP_address);// check if IP address is IPv6
        }
        catch(UnknownHostException e){
            isIPv6 = false;
        }
        return isIPv6;
    }
    
}
