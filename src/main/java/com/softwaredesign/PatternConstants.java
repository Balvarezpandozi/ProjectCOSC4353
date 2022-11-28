package com.softwaredesign;

import java.util.regex.Pattern;

public class PatternConstants {
    //COMMANDS
    public static Pattern listenByTimeRegex = Pattern.compile("-n listen -t (-w )?[0-9]+ [0-9]+", Pattern.CASE_INSENSITIVE); //[0-9]* -> amount of time in seconds, , [0-9]* -> device index
    public static Pattern listenByAmountOfPacketsRegex = Pattern.compile("-n listen -p (-w )?[0-9]+ [0-9]+", Pattern.CASE_INSENSITIVE); //[0-9]* -> amount of packets, [0-9]* -> device index
    public static Pattern helpRegex = Pattern.compile("help", Pattern.CASE_INSENSITIVE);
    public static Pattern exitRegex = Pattern.compile("exit", Pattern.CASE_INSENSITIVE);
    public static Pattern getPacketsRegex = Pattern.compile("-n show packets", Pattern.CASE_INSENSITIVE);
    public static Pattern readPacketsFromFileRegex = Pattern.compile("read -p [0-9a-z]+\\.pcap", Pattern.CASE_INSENSITIVE); //[0-9a-z]* -> file name
    public static Pattern filterPacketsRegex = Pattern.compile("-n listen -f (-w )?[0-9]+ [0-9]+", Pattern.CASE_INSENSITIVE);//[0-9]* -> amount of time in seconds, [0-9]* -> device index
    public static Pattern pingRegex = Pattern.compile("-h ping", Pattern.CASE_INSENSITIVE);
    public static Pattern getNetworkDevicesRegex = Pattern.compile("-n get -s local", Pattern.CASE_INSENSITIVE);
}
