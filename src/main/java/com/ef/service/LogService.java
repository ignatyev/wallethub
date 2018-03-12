package com.ef.service;

import com.ef.dao.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogService {

    private DAO dao = new DAO();


    /**
     * Reads log file and loads it to the DB
     *
     * @param accesslog a path to log file
     */
    public void loadLogFileToDB(String accesslog) {
        final int batchSize = 200000;

        List<LogRecord> batch = new ArrayList<>(batchSize);
        try (BufferedReader in = new BufferedReader(new FileReader(accesslog))) {
            String line;
            while ((line = in.readLine()) != null) {
                batch.add(new LogRecord(line));
                if (batch.size() == batchSize) {
                    dao.saveLogsBatch(batch);
                    batch.clear();
                }
            }
            dao.saveLogsBatch(batch);
            batch.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds such IPs that occur more than the threshold times since some date until
     * this date + 1 hour or + 1 day (depending on the duration parameter) NOT INCLUDING the right boundary
     * and saves them to the Blocked IPs table
     *
     * @param startDate starting date
     * @param duration hourly | daily
     * @param threshold max allowed count (exclusively)
     * @return a map of (IP, occurence times)
     */
    public Map<String, Integer> blockIPs(String startDate, String duration, String threshold) {
        Map<String, Integer> occurences = dao.findOccurences(startDate, duration, threshold);

        occurences.forEach((ip, count) -> {
            String comment = String.format("Accessed %d times since %s %s while threshold was %s",
                    count, startDate, duration, threshold);
            dao.insertIntoBlocked(ip, comment);
        });
        return occurences;
    }
}
