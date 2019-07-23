package com.lookup.cache;

import java.io.File;

public class CacheFactory {

    public static LookupCache initializeAndGetCache(File file, CacheType type) {
        switch (type) {
            case IN_MEMORY:
                return SegmentCache.getInstance(file, false);
            case REDIS:
                return new RedisCache();
            case MEMCAHCED:
                return new MemcachedCache();
        }
        return null;
    }
}
