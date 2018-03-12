package com.ef.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties load() {
        Properties prop = new Properties();
        String filename = "config.properties";
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + filename);
            }
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }
}