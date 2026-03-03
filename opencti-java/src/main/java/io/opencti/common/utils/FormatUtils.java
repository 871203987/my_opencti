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
 * 重写自: opencti-platform/opencti-graphql/src/utils/format.js
 * 格式化工具类
 * 
 * 原始函数:
 * - schedulingPeriodToMs: 调度周期转毫秒
 * - utcDate: UTC日期
 * - utcEpochTime: UTC纪元时间
 * - now: 当前时间
 * - nowTime: 当前时间字符串
 * - sinceNowInMinutes: 距今分钟数
 * - truncate: 字符串截断
 * - dateFormat: 日期格式化
 * - timeFormat: 时间格式化
 * - prepareDate: 日期准备
 * - yearFormat: 年份格式化
 * - monthFormat: 月份格式化
 * - dayFormat: 日期格式化
 */
public final class FormatUtils {

    public static final String FROM_START_STR = "1970-01-01T00:00:00.000Z";
    public static final String UNTIL_END_STR = "5138-11-16T09:46:40.000Z";
    public static final long FROM_START = 0L;
    public static final long UNTIL_END = 100000000000000L;
    
    private static final int DEFAULT_TRUNCATE_LIMIT = 64;

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("yyyy")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
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
     * 重写自: opencti-graphql/src/utils/format.js - truncate
     * 截断字符串到指定长度
     * 
     * @param str 要截断的字符串
     * @param limit 最大长度
     * @return 截断后的字符串
     */
    public static String truncate(String str, int limit) {
        return truncate(str, limit, true);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - truncate
     * 截断字符串到指定长度
     * 
     * @param str 要截断的字符串
     * @param limit 最大长度
     * @param withPoints 是否添加省略号
     * @return 截断后的字符串
     */
    public static String truncate(String str, int limit, boolean withPoints) {
        if (str == null || str.length() <= limit) {
            return str;
        }
        String trimmedStr = str.substring(0, limit);
        if (!withPoints) {
            return trimmedStr;
        }
        if (!trimmedStr.contains(" ")) {
            return trimmedStr + "...";
        }
        int lastSpaceIndex = trimmedStr.lastIndexOf(' ');
        return trimmedStr.substring(0, Math.min(trimmedStr.length(), lastSpaceIndex)) + "...";
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - truncate (默认)
     * 使用默认限制截断字符串
     * 
     * @param str 要截断的字符串
     * @return 截断后的字符串
     */
    public static String truncate(String str) {
        return truncate(str, DEFAULT_TRUNCATE_LIMIT, true);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - prepareDate
     * 准备日期字符串（格式化为标准格式）
     * 
     * @param instant 时间戳
     * @return 格式化后的日期字符串
     */
    public static String prepareDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return DATE_FORMATTER.format(instant);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - dateFormat
     * 格式化日期为完整格式
     * 
     * @param instant 时间戳
     * @return 格式化后的日期字符串
     */
    public static String dateFormat(Instant instant) {
        if (instant == null) {
            return null;
        }
        return DATE_FORMATTER.format(instant);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - timeFormat
     * 格式化时间为时间格式
     * 
     * @param instant 时间戳
     * @return 格式化后的时间字符串
     */
    public static String timeFormat(Instant instant) {
        if (instant == null) {
            return null;
        }
        return TIME_FORMATTER.format(instant);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - yearFormat
     * 格式化日期为年份格式
     * 
     * @param instant 时间戳
     * @return 年份字符串
     */
    public static String yearFormat(Instant instant) {
        if (instant == null) {
            return null;
        }
        return YEAR_FORMATTER.format(instant);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - monthFormat
     * 格式化日期为月份格式
     * 
     * @param instant 时间戳
     * @return 年月字符串
     */
    public static String monthFormat(Instant instant) {
        if (instant == null) {
            return null;
        }
        return MONTH_FORMATTER.format(instant);
    }

    /**
     * 重写自: opencti-graphql/src/utils/format.js - dayFormat
     * 格式化日期为日期格式
     * 
     * @param instant 时间戳
     * @return 年月日字符串
     */
    public static String dayFormat(Instant instant) {
        if (instant == null) {
            return null;
        }
        return DAY_FORMATTER.format(instant);
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
