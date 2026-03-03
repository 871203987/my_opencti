package io.opencti.common.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * 重写自: opencti-graphql/src/utils/format.js
 * 格式化工具类
 */
public final class FormatUtils {

    public static final String FROM_START_STR = "1970-01-01T00:00:00.000Z";
    public static final String UNTIL_END_STR = "5138-11-16T09:46:40.000Z";
    public static final long FROM_START = 0L;
    public static final long UNTIL_END = 100000000000000L;

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss")
            .withZone(ZoneId.of("UTC"));

    private static final Map<String, Long> SCHEDULING_PERIODS = new HashMap<>();
    static {
        SCHEDULING_PERIODS.put("PT1D", 86400000L);
        SCHEDULING_PERIODS.put("PT12H", 43200000L);
        SCHEDULING_PERIODS.put("PT6H", 21600000L);
        SCHEDULING_PERIODS.put("PT1H", 3600000L);
        SCHEDULING_PERIODS.put("PT30M", 1800000L);
        SCHEDULING_PERIODS.put("PT15M", 900000L);
        SCHEDULING_PERIODS.put("PT5M", 300000L);
    }

    private FormatUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - schedulingPeriodToMs
     * 将调度周期字符串转换为毫秒
     *
     * @param schedulingPeriod 调度周期字符串（如 PT1D, PT12H, PT6H 等）
     * @return 毫秒数
     */
    public static long schedulingPeriodToMs(String schedulingPeriod) {
        if (schedulingPeriod == null || schedulingPeriod.isEmpty()) {
            return 0L;
        }
        Long period = SCHEDULING_PERIODS.get(schedulingPeriod);
        return period != null ? period : 0L;
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - utcDate
     * 获取UTC日期时间
     *
     * @param instant 时间戳
     * @return UTC日期时间
     */
    public static ZonedDateTime utcDate(Instant instant) {
        if (instant == null) {
            return ZonedDateTime.now(ZoneId.of("UTC"));
        }
        return instant.atZone(ZoneId.of("UTC"));
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - utcEpochTime
     * 获取UTC纪元时间（毫秒）
     *
     * @param instant 时间戳
     * @return 纪元时间毫秒数
     */
    public static long utcEpochTime(Instant instant) {
        if (instant == null) {
            return Instant.now().toEpochMilli();
        }
        return instant.toEpochMilli();
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - now
     * 获取当前UTC时间的ISO格式字符串
     *
     * @return ISO格式的时间字符串
     */
    public static String now() {
        return ISO_FORMATTER.format(Instant.now());
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - nowTime
     * 获取当前UTC时间的时间格式字符串
     *
     * @return 时间格式字符串
     */
    public static String nowTime() {
        return TIME_FORMATTER.format(Instant.now());
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - isDateInRange
     * 检查日期是否在指定范围内
     *
     * @param startDate    开始日期
     * @param duration     持续时间
     * @param specificDate 要检查的日期
     * @return 如果日期在范围内则返回true
     */
    public static boolean isDateInRange(Instant startDate, Duration duration, Instant specificDate) {
        if (startDate == null || specificDate == null) {
            return true;
        }
        if (duration == null || duration.isZero() || duration.isNegative()) {
            return true;
        }
        Instant endDate = startDate.plus(duration);
        return !specificDate.isBefore(startDate) && !specificDate.isAfter(endDate);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - computeDateFromEventId
     * 从事件ID计算日期
     *
     * @param eventId 事件ID（格式：timestamp-index）
     * @return ISO格式的时间字符串
     */
    public static String computeDateFromEventId(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            return null;
        }
        String[] parts = eventId.split("-");
        if (parts.length == 0) {
            return null;
        }
        try {
            long timestamp = Long.parseLong(parts[0]);
            return ISO_FORMATTER.format(Instant.ofEpochMilli(timestamp));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - streamEventId
     * 生成流事件ID
     *
     * @param instant 时间戳
     * @param index   索引
     * @return 事件ID
     */
    public static String streamEventId(Instant instant, int index) {
        long timestamp = instant != null ? instant.toEpochMilli() : Instant.now().toEpochMilli();
        return timestamp + "-" + index;
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - sinceNowInMinutes
     * 计算从指定时间到现在经过的分钟数
     *
     * @param lastModified 最后修改时间
     * @return 经过的分钟数
     */
    public static long sinceNowInMinutes(Instant lastModified) {
        if (lastModified == null) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(lastModified, Instant.now());
    }

    /**
     * 格式化Instant为ISO字符串
     *
     * @param instant 时间戳
     * @return ISO格式字符串
     */
    public static String format(Instant instant) {
        if (instant == null) {
            return null;
        }
        return ISO_FORMATTER.format(instant);
    }

    /**
     * 解析ISO字符串为Instant
     *
     * @param dateStr ISO格式字符串
     * @return Instant对象
     */
    public static Instant parse(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return Instant.parse(dateStr);
    }

    /**
     * 获取当前时间戳（毫秒）
     *
     * @return 当前时间戳
     */
    public static long currentTimestamp() {
        return Instant.now().toEpochMilli();
    }
}
