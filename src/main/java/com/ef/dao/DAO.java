package com.ef.dao;

import com.ef.conf.PropertiesLoader;
import com.ef.service.LogRecord;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DAO {
    private static final String DB_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true";
    private Connection connection;

    {
        Properties properties = PropertiesLoader.load();
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String db = properties.getProperty("db");

        try {
            connection = DriverManager.getConnection(String.format(DB_URL, host, port, db), user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveLogsBatch(List<LogRecord> batch) {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO log(dt, ip) VALUES (?, ?)")) {
            for (LogRecord logRecord : batch) {
                statement.setString(1, logRecord.getDate());
                statement.setString(2, logRecord.getIp());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Map<String, Integer> findOccurences(String startDate, String duration, String threshold) {
        Map<String, Integer> result = new HashMap<>();
        String sql =
                "SELECT ip, COUNT(1) AS count " +
                        "FROM log WHERE dt >= '%s' AND dt < DATE_ADD('%s', INTERVAL 1 %s) " +
                        "GROUP BY ip HAVING COUNT(1) > %s";
        String interval = "HOURLY".equals(duration.toUpperCase()) ? "HOUR" : "DAY";
        try (Statement statement = connection.createStatement()) {
            String query = String.format(sql, startDate, startDate, interval, threshold);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                result.put(rs.getString("ip"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void insertIntoBlocked(String ip, String comment) {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO blocked VALUES (?, ?)")) {
            statement.setString(1, ip);
            statement.setString(2, comment);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
