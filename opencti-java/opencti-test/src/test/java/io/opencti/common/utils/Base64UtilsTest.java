package io.opencti.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/utils/test_base64.js
 * Base64工具类测试
 */
class Base64UtilsTest {

    @Test
    void testEncodeString() {
        String input = "test";
        String encoded = Base64Utils.encodeString(input);
        
        assertNotNull(encoded);
        assertEquals("dGVzdA==", encoded);
    }

    @Test
    void testDecodeToString() {
        String encoded = "dGVzdA==";
        String decoded = Base64Utils.decodeToString(encoded);
        
        assertEquals("test", decoded);
    }

    @Test
    void testEncodeDecodeRoundTrip() {
        String input = "Hello, World!";
        String encoded = Base64Utils.encodeString(input);
        String decoded = Base64Utils.decodeToString(encoded);
        
        assertEquals(input, decoded);
    }

    @Test
    void testEncodeNull() {
        assertNull(Base64Utils.encodeString(null));
    }

    @Test
    void testDecodeNull() {
        assertNull(Base64Utils.decodeToString(null));
        assertNull(Base64Utils.decodeToString(""));
    }

    @Test
    void testEncodeObject() {
        Map<String, Object> obj = Map.of("name", "test", "value", 123);
        String encoded = Base64Utils.encode(obj);
        
        assertNotNull(encoded);
        
        Map<String, Object> decoded = Base64Utils.decode(encoded, Map.class);
        assertEquals("test", decoded.get("name"));
        assertEquals(123, decoded.get("value"));
    }
}
