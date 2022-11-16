package com.softwaredesign;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
//gets the Ip Address of the host computer and the MAC address of that computer 
//protocol for mapping an Ip address to a physical MAC address on a local network
//Every computer is assigned an IP address and some local server's IP adresses a
public class getHostsIpAndMac { 
    private static int port = 80;

    public static void main(String[] args) {
        getNetworkDevices();
    }

    static void getNetworkDevices(){
        Socket socket = new Socket(); // Create a new socket

        try { // Try to connect to the socket
            Process process = Runtime.getRuntime().exec("arp -a"); // Run the arp -a command
            process.waitFor(); // Wait for the process to finish
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); // Create a new buffered reader to read the output of the process

            String ip = null;
            @SuppressWarnings("rawtypes")
            List ipList = new ArrayList<>(); // List<> Array to hold dynamic IP Addresses
            while ((ip = reader.readLine()) != null) { // While the reader is not null
                ip = ip.trim();     // Trim the data
                if (!ip.equals("")) { // If the data is not empty
                    if (!ip.equals("")) { // If the data is not empty
                        // Remove all the unwanted spaces between data provided by 
                        // the ARP Table when it is generated.
                        while (ip.contains("  ")) { ip = ip.trim().replace("  ", " "); }
                        // Split each data line into a String Array for processing
                        String[] dataArray = ip.split(" ");
                        // For console output display only...
                        if (dataArray[0].toLowerCase().startsWith("interface:")) { // If the data is an interface
                            System.out.println("Locating Devices Connected To: " + dataArray[1]); // Display the interface of local hosts and router
                        }
                        // If the data line contains the word "dynamic"
                        // then add the IP address on that line to the 
                        // List<> Array...
                        if (dataArray[2].equalsIgnoreCase("dynamic")) {
                             // Add the IP Address to the List<> Array
                            ipList.add(dataArray[0]);// Add the IP Address to the List<> Array
                            // For console output display only...
                            System.out.println("Device Located On IP: " + dataArray[0]);// Display the IP address in this case is default gate way 
                        }
                    }
                }
            }
            // Close the Reader
            reader.close();

            // try to connect to the device....
            // You'll need to play with this.
            try {
                for (int i = 0; i < ipList.size(); i++) {
                    // Try to connect to the socket
                    socket.connect(new InetSocketAddress((String) ipList.get(i), port), 5000);
                    // If the connection is successful, then display the IP Address
                    System.out.println("Device Connected On IP: " + ipList.get(i));
                    // Close the socket
                    socket.close();
                    
                }
            } catch (ConnectException | SocketTimeoutException ex) {
                // If the connection is unsuccessful, then display the IP Address
                for(int i = 0; i < ipList.size(); i++){
                    System.out.println("Device Not Connected On IP: " + ipList.get(i));
                }
                System.out.println("\nSOCKET ERROR - " + ex.getMessage());
            }
        } catch (IOException | InterruptedException e) { 
            System.out.println("\nPROCESS/READER ERROR - " + e.getMessage()); 
        }
    }
}
