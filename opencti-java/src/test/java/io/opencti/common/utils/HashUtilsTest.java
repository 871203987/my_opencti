package io.opencti.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/utils/test_hash.js
 * 哈希工具类测试
 */
class HashUtilsTest {

    @Test
    void testHashSHA256() {
        String input = "test";
        String hash = HashUtils.hashSHA256(input);
        
        assertNotNull(hash);
        assertEquals(64, hash.length());
        assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", hash);
    }

    @Test
    void testHashSHA256Null() {
        assertNull(HashUtils.hashSHA256(null));
    }

    @Test
    void testCompareHashSHA256() {
        String input = "test";
        String hash = HashUtils.hashSHA256(input);
        
        assertTrue(HashUtils.compareHashSHA256(input, hash));
        assertFalse(HashUtils.compareHashSHA256("wrong", hash));
        assertFalse(HashUtils.compareHashSHA256(null, hash));
        assertFalse(HashUtils.compareHashSHA256(input, null));
    }

    @Test
    void testHashMD5() {
        String input = "test";
        String hash = HashUtils.hashMD5(input);
        
        assertNotNull(hash);
        assertEquals(32, hash.length());
        assertEquals("098f6bcd4621d373cade4e832627b4f6", hash);
    }

    @Test
    void testHashSHA1() {
        String input = "test";
        String hash = HashUtils.hashSHA1(input);
        
        assertNotNull(hash);
        assertEquals(40, hash.length());
        assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", hash);
    }

    @Test
    void testHashSHA256Empty() {
        String hash = HashUtils.hashSHA256("");
        assertNotNull(hash);
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", hash);
    }
}
