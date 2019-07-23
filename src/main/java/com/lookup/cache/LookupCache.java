package com.lookup.cache;

import com.lookup.data.SegmentConfig;

/**
 * Interface to be implemented by your solution
 */
public interface LookupCache {
    SegmentConfig[] getSegmentFor(final String orgKey, final String paramKey);
    SegmentConfig[] getSegmentFor(final String orgKey, final String paramKey, final String paramValKey);
}
