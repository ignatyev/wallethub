package com.ef.conf;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ArgumentsParser {

    private final Map<String, String> arguments;

    public ArgumentsParser(String[] args) {
        arguments = Arrays.stream(args).map(Argument::new)
                .collect(Collectors.toMap(Argument::getKey, Argument::getValue));
    }

    Map<String, String> getArguments() {
        return arguments;
    }

    public String get(String key) {
        String value = arguments.get(key);
        if (value == null)
            throw new IllegalArgumentException(String.format("Argument %s not found", key));
        return value;
    }
}
