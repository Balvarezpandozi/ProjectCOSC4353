package com.softwaredesign.filter;

import org.apache.commons.lang3.StringUtils;

public class FilterBuilder {

    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String PORTRANGE = "portrange";
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
        this.host = HOST + StringUtils.SPACE + host;
        return this;
    }

    public FilterBuilder setPort(String port) {
        this.port = PORT + StringUtils.SPACE + port;
        return this;
    }

    public FilterBuilder setPortRange(String portFrom, String portTo) {
        this.port = PORTRANGE + StringUtils.SPACE + portFrom + "-" + portTo;
        return this;
    }

    public FilterBuilder setIPVersion(String ipVersion) {
        this.ipVersion = ipVersion;
        return this;
    }

    public String build() {
        String filter;
        if (StringUtils.isNotEmpty(ipVersion)) {
            filter = ipVersion;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.isNotEmpty(protocol) ? protocol+StringUtils.SPACE : StringUtils.EMPTY);
            sb.append(StringUtils.isNotEmpty(dir) ? dir+StringUtils.SPACE : StringUtils.EMPTY);
            sb.append(StringUtils.isNotEmpty(host) ? host : StringUtils.EMPTY);
            sb.append(StringUtils.isNotEmpty(port) ? port : StringUtils.EMPTY);
            filter = sb.toString();
        }

        return filter.trim();
    }
}
