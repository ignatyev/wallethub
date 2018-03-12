package com.ef.conf;

import com.ef.conf.ArgumentsParser;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ArgumentsParserTest {

    @Test
    public void parse() {
        ArgumentsParser parser = new ArgumentsParser(new String[]{"--key=value"});
        HashMap<String, String> expected = new HashMap<>();
        expected.put("key", "value");
        assertEquals(expected, parser.getArguments());
    }

}