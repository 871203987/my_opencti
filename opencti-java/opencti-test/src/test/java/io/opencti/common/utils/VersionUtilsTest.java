package io.opencti.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/utils/test_version.js
 * 版本工具类测试
 */
class VersionUtilsTest {

    @Test
    void testIsCompatibleVersionWithMinimal() {
        assertTrue(VersionUtils.isCompatibleVersionWithMinimal("6.0.0", "5.0.0"));
        assertTrue(VersionUtils.isCompatibleVersionWithMinimal("5.0.0", "5.0.0"));
        assertFalse(VersionUtils.isCompatibleVersionWithMinimal("4.0.0", "5.0.0"));
    }

    @Test
    void testCompareVersions() {
        assertEquals(0, VersionUtils.compareVersions("1.0.0", "1.0.0"));
        assertTrue(VersionUtils.compareVersions("2.0.0", "1.0.0") > 0);
        assertTrue(VersionUtils.compareVersions("1.0.0", "2.0.0") < 0);
        assertTrue(VersionUtils.compareVersions("1.2.0", "1.1.0") > 0);
        assertTrue(VersionUtils.compareVersions("1.1.5", "1.1.3") > 0);
    }

    @Test
    void testCompareVersionsWithNull() {
        assertEquals(0, VersionUtils.compareVersions(null, null));
        assertTrue(VersionUtils.compareVersions("1.0.0", null) > 0);
        assertTrue(VersionUtils.compareVersions(null, "1.0.0") < 0);
    }

    @Test
    void testIsVersionInRange() {
        assertTrue(VersionUtils.isVersionInRange("2.0.0", "1.0.0", "3.0.0"));
        assertTrue(VersionUtils.isVersionInRange("1.0.0", "1.0.0", "2.0.0"));
        assertFalse(VersionUtils.isVersionInRange("3.0.0", "1.0.0", "2.0.0"));
        assertFalse(VersionUtils.isVersionInRange("0.5.0", "1.0.0", "2.0.0"));
    }

    @Test
    void testGetMajorVersion() {
        assertEquals(6, VersionUtils.getMajorVersion("6.9.5"));
        assertEquals(1, VersionUtils.getMajorVersion("1.0.0"));
    }

    @Test
    void testGetMinorVersion() {
        assertEquals(9, VersionUtils.getMinorVersion("6.9.5"));
        assertEquals(0, VersionUtils.getMinorVersion("1.0.0"));
    }

    @Test
    void testGetPatchVersion() {
        assertEquals(5, VersionUtils.getPatchVersion("6.9.5"));
        assertEquals(0, VersionUtils.getPatchVersion("1.0.0"));
    }

    @Test
    void testGetPreRelease() {
        assertEquals("SNAPSHOT", VersionUtils.getPreRelease("6.9.5-SNAPSHOT"));
        assertEquals("rc1", VersionUtils.getPreRelease("1.0.0-rc1"));
        assertNull(VersionUtils.getPreRelease("1.0.0"));
    }

    @Test
    void testIsCompatibleVersionWithMinimalNull() {
        assertFalse(VersionUtils.isCompatibleVersionWithMinimal(null, "1.0.0"));
        assertFalse(VersionUtils.isCompatibleVersionWithMinimal("1.0.0", null));
        assertFalse(VersionUtils.isCompatibleVersionWithMinimal(null, null));
    }
}
