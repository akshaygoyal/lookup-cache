package com.lookup.data;


import org.codehaus.jackson.annotate.JsonProperty;

public class Segment {

    @JsonProperty("segmentId")
    private String segmentId;

    public Segment(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    @Override
    public String toString() {
        return segmentId;
    }
}
