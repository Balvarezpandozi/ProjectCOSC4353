package com.softwaredesign.filter;

public class FilterBuilder {

    public static final String WHITESPACE = " ";
    public static final String HOST = "host";
    public static final String PORT = "port";
    private String protocol;
    private String dir;
    private String host;
    private String port;
    private String ipVersion;

    public FilterBuilder setPrototypeQualifier(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public FilterBuilder setDirQualifier(String dir) {
        this.dir = dir;
        return this;
    }

    public FilterBuilder setHost(String host) {
        this.host = HOST + WHITESPACE + host;
        return this;
    }

    public FilterBuilder setPort(String port) {
        this.port = HOST + WHITESPACE + port;
        return this;
    }

    public FilterBuilder setIPVersion(String ipVersion) {
        this.ipVersion = ipVersion;
        return this;
    }

    public String build() {
        String filter =
                protocol + WHITESPACE
                + dir + WHITESPACE
                + host;
        return filter;
    }
}
