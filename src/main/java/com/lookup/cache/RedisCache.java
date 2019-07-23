package com.lookup.cache;

import com.lookup.data.SegmentConfig;

public class RedisCache implements LookupCache {
    @Override
    public SegmentConfig[] getSegmentFor(String orgKey, String paramKey) {
        return new SegmentConfig[0];
    }

    @Override
    public SegmentConfig[] getSegmentFor(String orgKey, String paramKey, String paramValKey) {
        return new SegmentConfig[0];
    }
}
