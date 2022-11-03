package com.softwaredesign.filter;

import java.util.Objects;
import java.util.Scanner;

public class PacketFilterUtility {

    private Scanner scanner;
    private String input;
    private FilterBuilder filterBuilder;

    public PacketFilterUtility() {
        scanner = new Scanner(System.in);
        filterBuilder = new FilterBuilder();
    }

    public String createFilter() {

        System.out.println("Filter by protocol? (y/n)");
        input = scanner.nextLine();
        if (isAffirmative(input)) {
            System.out.println("Enter protocol: ");
            input = scanner.nextLine();
            filterBuilder.setPrototypeQualifier(input);
        }

        System.out.println("Filter by source/destination? (y/n)");
        input = scanner.nextLine();
        if (isAffirmative(input)) {
            System.out.println("Enter src or dst: ");
            input = scanner.nextLine();
            filterBuilder.setDirQualifier(input);
        }

        System.out.println("Filter by host? (y/n)");
        input = scanner.nextLine();
        if (isAffirmative(input)) {
            System.out.println("Enter host ip: ");
            input = scanner.nextLine();
            filterBuilder.setHost(input);
        }

        System.out.println("Filter by port? (y/n)");
        input = scanner.nextLine();
        if (isAffirmative(input)) {
            System.out.println("Enter port: ");
            input = scanner.nextLine();
            filterBuilder.setPort(input);
        }

        System.out.println("Filter by ip version? (y/n)");
        input = scanner.nextLine();
        if (isAffirmative(input)) {
            System.out.println("Enter ip/ip6: ");
            input = scanner.nextLine();
            filterBuilder.setIPVersion(input);
        }
        
        String filter = filterBuilder.build();
        return filter;
    }

    public boolean isAffirmative(String input) {
        if (Objects.isNull(input)) {
            return false;
        }
        String lowerCaseInput = input.toLowerCase();
        return lowerCaseInput.equals("y")
                || lowerCaseInput.equals("yes");
    }
}
