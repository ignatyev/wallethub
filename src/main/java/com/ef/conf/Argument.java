package com.ef.conf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Argument {

    private static final Pattern PATTERN = Pattern.compile("^--(.+)=(.+)$");
    private final String key;
    private final String value;

    public Argument(String arg) {
        Matcher matcher = PATTERN.matcher(arg);
        if (!matcher.matches())
            throw new IllegalArgumentException(String.format("Argument {%s} does not match pattern {%s}", arg, PATTERN));
        key = matcher.group(1);
        value = matcher.group(2);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
