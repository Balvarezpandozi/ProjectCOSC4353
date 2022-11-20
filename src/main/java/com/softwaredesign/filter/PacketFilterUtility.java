package com.softwaredesign.filter;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketFilterUtility {

    public static final String PORTRANGE_PATTERN = ".*-.*";
    private Scanner scanner;
    private String input;
    private FilterBuilder filterBuilder;

    public PacketFilterUtility(Scanner scanner) {
        this.scanner = scanner;
        filterBuilder = new FilterBuilder();
    }

    public String createFilter() {

        System.out.println("Filter by ip version? (y/n)");
        input = scanner.nextLine();
        if (isAffirmative(input)) {
            System.out.println("Enter ip/ip6: ");
            input = scanner.nextLine();
            filterBuilder.setIPVersion(input);
        } else {
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
            } else {
                System.out.println("Filter by port/port range? (y/n)");
                input = scanner.nextLine();
                if (isAffirmative(input)) {
                    System.out.println("Enter port/port range: ");
                    input = scanner.nextLine();
                    Pattern pattern = Pattern.compile(PORTRANGE_PATTERN, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(input);
                    boolean portRangeInput = matcher.find();
                    if(portRangeInput){
                        String[] res = input.split("-");
                        filterBuilder.setPortRange(res[0].trim(),res[1].trim());
                    } else {
                        filterBuilder.setPort(input.trim());
                    }
                } else {
//                    System.out.println("Enter net: ");
//                    input = scanner.nextLine();
//                    filterBuilder.setNet(input);
                }
            }
        }

        
        String filter = filterBuilder.build();
        return filter;
    }

    public boolean isAffirmative(String input) {
        if (Objects.isNull(input) || StringUtils.isEmpty(input)) {
            return false;
        }
        String lowerCaseInput = input.toLowerCase();
        return lowerCaseInput.equals("y")
                || lowerCaseInput.equals("yes");
    }
}
