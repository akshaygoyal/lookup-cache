package com.lookup.cache;

import com.lookup.data.Organization;
import com.lookup.data.Parameter;
import com.lookup.data.SegmentConfig;
import com.lookup.parser.SegmentParser;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SegmentCache implements LookupCache {

    private static SegmentCache instance;

    private Map<String, Map<String, Map<String, List<SegmentConfig>>>> cache;

    public static SegmentCache getInstance(File file, boolean forceRefresh) {
        if (instance == null || forceRefresh) {
            instance = new SegmentCache(file);
        }
        return instance;
    }

    private SegmentCache(File file) {
        cache = new HashMap<>();
        init(file);
    }

    private void init(File file) {
        try {
            Organization[] organizations = SegmentParser.parse(file);

            for (Organization organization : organizations) {

                Map<String, Map<String, List<SegmentConfig>>> paramMap = new HashMap<>();
                for (Parameter parameter : organization.getParameters()) {

                    List<SegmentConfig> segmentConfigList = parameter.getSegmentConfigList();
                    Map<String, List<SegmentConfig>> segmentConfigMap = new HashMap<>();
                    for (SegmentConfig segmentConfig : segmentConfigList) {

                        Set<String> paramValues = new HashSet<>();
                        if (segmentConfig.getParameterValue().contains("\n")) {
                            paramValues.addAll(Arrays.asList(segmentConfig.getParameterValue().split("\n")));
                        } else {
                            paramValues.add(segmentConfig.getParameterValue());
                        }

                        for (String paramValue : paramValues) {
                            List<SegmentConfig> newSegmentConfigList = new ArrayList<>();
                            if (segmentConfigMap.containsKey(paramValue)) {
                                newSegmentConfigList = segmentConfigMap.get(paramValue);
                            }
                            SegmentConfig segConf = new SegmentConfig();
                            segConf.setParameterValue(paramValue);
                            segConf.setSegment(segmentConfig.getSegment());
                            newSegmentConfigList.add(segConf);
                            segmentConfigMap.put(paramValue, newSegmentConfigList);
                        }
                    }
                    paramMap.put(parameter.getParameterName(), segmentConfigMap);
                }
                cache.put(organization.getOrgId(), paramMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SegmentConfig[] getSegmentFor(String orgKey, String paramKey) {
        try {
            return cache.get(orgKey).get(paramKey).get("").stream().toArray(SegmentConfig[]::new);
        } catch (NullPointerException e) {
            return new SegmentConfig[]{};
        }
    }

    @Override
    public SegmentConfig[] getSegmentFor(String orgKey, String paramKey, String paramValKey) {
        try {
            return cache.get(orgKey).get(paramKey).get(paramValKey).stream().toArray(SegmentConfig[]::new);
        } catch (NullPointerException e) {
            return new SegmentConfig[]{};
        }
    }

}

