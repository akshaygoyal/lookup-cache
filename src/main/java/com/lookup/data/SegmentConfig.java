package com.lookup.data;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.Map;

@JsonRootName("segmentConfig")
public class SegmentConfig {

    private String parameterValue;

    private Segment segment;

//    public SegmentConfig(String parameterValue, Segment segment) {
//        this.parameterValue = parameterValue;
//        this.segment = segment;
//    }

    @JsonAnySetter
    public void anySetter(String key, Map<String, Segment> map) {
       setParameterValue(key);
       setSegment(map.values().iterator().next());
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    @Override
    public String toString() {
        return segment.toString();
    }
}
