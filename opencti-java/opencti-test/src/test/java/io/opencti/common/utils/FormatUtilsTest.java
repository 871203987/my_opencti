package io.opencti.common.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/utils/test_format.js
 * 格式化工具类测试
 */
class FormatUtilsTest {

    @Test
    void testSchedulingPeriodToMs() {
        assertEquals(86400000L, FormatUtils.schedulingPeriodToMs("PT1D"));
        assertEquals(43200000L, FormatUtils.schedulingPeriodToMs("PT12H"));
        assertEquals(21600000L, FormatUtils.schedulingPeriodToMs("PT6H"));
        assertEquals(3600000L, FormatUtils.schedulingPeriodToMs("PT1H"));
        assertEquals(1800000L, FormatUtils.schedulingPeriodToMs("PT30M"));
        assertEquals(900000L, FormatUtils.schedulingPeriodToMs("PT15M"));
        assertEquals(300000L, FormatUtils.schedulingPeriodToMs("PT5M"));
    }

    @Test
    void testSchedulingPeriodToMsInvalid() {
        assertEquals(0L, FormatUtils.schedulingPeriodToMs("INVALID"));
        assertEquals(0L, FormatUtils.schedulingPeriodToMs(null));
        assertEquals(0L, FormatUtils.schedulingPeriodToMs(""));
    }

    @Test
    void testNow() {
        String now = FormatUtils.now();
        assertNotNull(now);
        assertTrue(now.contains("T"));
        assertTrue(now.endsWith("Z"));
    }

    @Test
    void testUtcDate() {
        Instant instant = Instant.parse("2024-01-01T00:00:00.000Z");
        var zonedDateTime = FormatUtils.utcDate(instant);
        
        assertNotNull(zonedDateTime);
        assertEquals(2024, zonedDateTime.getYear());
        assertEquals(1, zonedDateTime.getMonthValue());
        assertEquals(1, zonedDateTime.getDayOfMonth());
    }

    @Test
    void testUtcEpochTime() {
        Instant instant = Instant.parse("2024-01-01T00:00:00.000Z");
        long epochTime = FormatUtils.utcEpochTime(instant);
        
        assertEquals(instant.toEpochMilli(), epochTime);
    }

    @Test
    void testIsDateInRange() {
        Instant start = Instant.parse("2024-01-01T00:00:00.000Z");
        Duration duration = Duration.ofDays(10);
        Instant inside = Instant.parse("2024-01-05T00:00:00.000Z");
        Instant outside = Instant.parse("2024-01-15T00:00:00.000Z");
        
        assertTrue(FormatUtils.isDateInRange(start, duration, inside));
        assertFalse(FormatUtils.isDateInRange(start, duration, outside));
    }

    @Test
    void testComputeDateFromEventId() {
        String eventId = "1704067200000-0";
        String date = FormatUtils.computeDateFromEventId(eventId);
        
        assertNotNull(date);
        assertTrue(date.startsWith("2024"));
    }

    @Test
    void testComputeDateFromEventIdInvalid() {
        assertNull(FormatUtils.computeDateFromEventId(null));
        assertNull(FormatUtils.computeDateFromEventId(""));
        assertNull(FormatUtils.computeDateFromEventId("invalid"));
    }

    @Test
    void testStreamEventId() {
        Instant instant = Instant.parse("2024-01-01T00:00:00.000Z");
        String eventId = FormatUtils.streamEventId(instant, 0);
        
        assertNotNull(eventId);
        assertTrue(eventId.contains("-"));
        assertEquals("1704067200000-0", eventId);
    }

    @Test
    void testFormat() {
        Instant instant = Instant.parse("2024-01-01T00:00:00.000Z");
        String formatted = FormatUtils.format(instant);
        
        assertEquals("2024-01-01T00:00:00.000Z", formatted);
    }

    @Test
    void testParse() {
        Instant instant = FormatUtils.parse("2024-01-01T00:00:00.000Z");
        
        assertNotNull(instant);
        assertEquals(Instant.parse("2024-01-01T00:00:00.000Z"), instant);
    }

    @Test
    void testConstants() {
        assertEquals("1970-01-01T00:00:00.000Z", FormatUtils.FROM_START_STR);
        assertEquals("5138-11-16T09:46:40.000Z", FormatUtils.UNTIL_END_STR);
        assertEquals(0L, FormatUtils.FROM_START);
        assertEquals(100000000000000L, FormatUtils.UNTIL_END);
    }
}
