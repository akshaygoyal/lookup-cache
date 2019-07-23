package com.lookup.data;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.List;

@JsonRootName("organisation")
public class Organization {

    private String orgId;

    private List<Parameter> parameters;


    @JsonAnySetter
    public void anySetter(String key, List<Parameter> parameterList) {
        setOrgId(key);
        setParameters(parameterList);
    }


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }


}
