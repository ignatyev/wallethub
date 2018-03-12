package com.ef.service;

public class LogRecord {
//    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final int FIELDS_COUNT = 5;

    private String date;
    private String ip;

    public LogRecord(String line) {
        String[] split = line.split("\\|");
        if (split.length != FIELDS_COUNT)
            System.err.println(
                    String.format("Wrong log record format for line: {%s} - expected %d fields, found %d", line, FIELDS_COUNT, split.length));
        date =/* new SimpleDateFormat(DATE_FORMAT).parse*/(split[0]);
        ip = split[1];
    }

    public String getDate() {
        return date;
    }

    public String getIp() {
        return ip;
    }
}
