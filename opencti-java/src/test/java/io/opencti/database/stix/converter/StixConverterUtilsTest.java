package io.opencti.database.stix.converter;

import io.opencti.common.exception.FunctionalException;
import io.opencti.common.types.stix.StixDate;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StixConverterUtils单元测试
 */
class StixConverterUtilsTest {

    @Test
    void testAssertType_SameType() {
        // 相同类型不应该抛出异常
        assertDoesNotThrow(() -> StixConverterUtils.assertType("Malware", "Malware"));
    }

    @Test
    void testAssertType_DifferentType() {
        // 不同类型应该抛出异常
        assertThrows(FunctionalException.class, () -> StixConverterUtils.assertType("Malware", "Report"));
    }

    @Test
    void testConvertToStixDate_Date() {
        Date date = new Date();
        String result = StixConverterUtils.convertToStixDate(date);
        assertNotNull(result);
        assertTrue(result.contains("T")); // ISO格式包含T
    }

    @Test
    void testConvertToStixDate_String() {
        String dateStr = "2024-01-15T10:30:00.000Z";
        String result = StixConverterUtils.convertToStixDate(dateStr);
        assertEquals(dateStr, result);
    }

    @Test
    void testConvertToStixDate_BoundaryDate() {
        // 边界日期应该返回null
        String result = StixConverterUtils.convertToStixDate("1970-01-01T00:00:00.000Z");
        assertNull(result);
    }

    @Test
    void testConvertToStixDate_Instant() {
        Instant instant = Instant.now();
        String result = StixConverterUtils.convertToStixDate(instant);
        assertNotNull(result);
    }

    @Test
    void testConvertToStixDate_StixDate() {
        StixDate stixDate = new StixDate("2024-01-15T10:30:00.000Z");
        String result = StixConverterUtils.convertToStixDate(stixDate);
        assertNotNull(result);
    }

    @Test
    void testConvertToStixDate_Null() {
        assertNull(StixConverterUtils.convertToStixDate((Date) null));
        assertNull(StixConverterUtils.convertToStixDate((String) null));
        assertNull(StixConverterUtils.convertToStixDate((Instant) null));
    }

    @Test
    void testBuildStixId() {
        String prefix = "malware--";
        String uuid = "12345678-1234-1234-1234-123456789abc";
        String result = StixConverterUtils.buildStixId(prefix, uuid);
        assertEquals(prefix + uuid, result);
    }

    @Test
    void testBuildStixId_WithExistingPrefix() {
        String prefix = "malware--";
        String stixId = "malware--12345678-1234-1234-1234-123456789abc";
        String result = StixConverterUtils.buildStixId(prefix, stixId);
        assertEquals(stixId, result);
    }

    @Test
    void testBuildStixId_NullUuid() {
        String result = StixConverterUtils.buildStixId("malware--", null);
        assertNotNull(result);
        assertTrue(result.startsWith("malware--"));
    }
}
