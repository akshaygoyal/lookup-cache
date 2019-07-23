package com.lookup.parser;

import com.lookup.data.Organization;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SegmentParser {

    public static Organization[] parse(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Organization[] organizations = objectMapper.readValue(file, Organization[].class);
        return organizations;
    }
}
