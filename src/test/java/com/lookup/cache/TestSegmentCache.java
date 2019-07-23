package com.lookup.cache;

import com.lookup.data.SegmentConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestSegmentCache {

    private static SegmentCache cache;

    @BeforeClass
    public static void initCache() {
        ClassLoader classLoader = TestSegmentCache.class.getClassLoader();
        File file = new File(classLoader.getResource("data.json").getFile());
        cache = (SegmentCache) CacheFactory.initializeAndGetCache(file, CacheType.IN_MEMORY);
    }

    @Test
    public void testEmptyParamValue() {
        SegmentConfig[] segments = cache.getSegmentFor("6lkb2cv", "Edu");
        Assert.assertEquals(0, segments.length);
    }


    @Test
    public void testValidParamValue() {
        SegmentConfig[] segments = cache.getSegmentFor("6lkb2cv", "gen", "Female");
        Assert.assertEquals(1, segments.length);
        Assert.assertEquals("Female", segments[0].getParameterValue());
        Assert.assertEquals("dem.g.f", segments[0].getSegment().getSegmentId());
    }

    @Test
    public void testNewLineParamValue() {
        SegmentConfig[] segments = cache.getSegmentFor("6lkb2cv", "Edu", "graduate");
        Assert.assertEquals(1, segments.length);
        Assert.assertEquals("graduate", segments[0].getParameterValue());
        Assert.assertEquals("intr.edu", segments[0].getSegment().getSegmentId());
    }

    @Test
    public void testMultipleSegmentConfigs() {
        SegmentConfig[] segments = cache.getSegmentFor("bkie9g1", "cat", "cooking");
        Assert.assertEquals(5, segments.length);
        Set<String> expectedSet = new HashSet<>(Arrays.asList(new String[]{"int.shop.cpg.groc", "dem.life.gbuy", "intr.food.gro", "intr.food.cook", "intr.food"}));
        Set<String> actualSet = new HashSet<>();
        for (SegmentConfig segmentConfig : segments) {
            actualSet.add(segmentConfig.getSegment().getSegmentId());
        }
        Assert.assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testExplicitEmptyParamValue() {
        SegmentConfig[] segments = cache.getSegmentFor("gdoaq51", "cat", "");
        Assert.assertEquals(1, segments.length);
        Assert.assertEquals("", segments[0].getParameterValue());
        Assert.assertEquals("zz_trash", segments[0].getSegment().getSegmentId());
    }

    @Test
    public void testIncorrectParamValue() {
        SegmentConfig[] segments = cache.getSegmentFor("gdoaq51", "cat", "entertainment");
        Assert.assertEquals(0, segments.length);
    }


    @Test
    public void testCorrectParamValue() {
        SegmentConfig[] segments = cache.getSegmentFor("gdoaq51", "gen", "male");
        Assert.assertEquals(1, segments.length);
        Assert.assertEquals("male", segments[0].getParameterValue());
        Assert.assertEquals("dem.g.m", segments[0].getSegment().getSegmentId());
    }

    @Test
    public void testMissingOrganisation() {
        SegmentConfig[] segments = cache.getSegmentFor("dummy-org", "cat", "entertainment");
        Assert.assertEquals(0, segments.length);
    }

    @Test
    public void testMissingParameterKey() {
        SegmentConfig[] segments = cache.getSegmentFor("gdoaq51", "dummy-param-key", "entertainment");
        Assert.assertEquals(0, segments.length);
    }
}
