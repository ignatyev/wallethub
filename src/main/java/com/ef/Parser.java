package com.ef;

import com.ef.conf.ArgumentsParser;
import com.ef.service.LogService;

import java.util.Map;

public class Parser {

    public static void main(String[] args) {
        ArgumentsParser argsParser = new ArgumentsParser(args);
        String startDate = argsParser.get("startDate");
        String duration = argsParser.get("duration");
        String threshold = argsParser.get("threshold");
        String accesslog = argsParser.get("accesslog");

        LogService logService = new LogService();
        logService.loadLogFileToDB(accesslog);

        Map<String, Integer> blockIPs = logService.blockIPs(startDate, duration, threshold);
        System.out.println(blockIPs.keySet());
    }

}
