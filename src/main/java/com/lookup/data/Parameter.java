package com.lookup.data;


import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.List;

@JsonRootName("parameter")
public class Parameter {

    private String parameterName;

    private List<SegmentConfig> segmentConfigList;

    @JsonAnySetter
    public void anySetter(String key, List<SegmentConfig> list) {
        setParameterName(key);
        setSegmentConfigList(list);
    }


    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public List<SegmentConfig> getSegmentConfigList() {
        return segmentConfigList;
    }

    public void setSegmentConfigList(List<SegmentConfig> segmentConfigList) {
        this.segmentConfigList = segmentConfigList;
    }
}
