package io.opencti.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 重写自: opencti-graphql/src/utils/version.ts
 * 版本工具类
 */
public final class VersionUtils {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(?:-(.+))?");

    private VersionUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/version.ts - isCompatibleVersionWithMinimal
     * 检查版本是否大于等于最小版本
     *
     * @param version        当前版本
     * @param minimalVersion 最小版本
     * @return 如果当前版本大于等于最小版本则返回true
     */
    public static boolean isCompatibleVersionWithMinimal(String version, String minimalVersion) {
        if (version == null || minimalVersion == null) {
            return false;
        }
        return compareVersions(version, minimalVersion) >= 0;
    }

    /**
     * 比较两个语义化版本
     *
     * @param version1 第一个版本
     * @param version2 第二个版本
     * @return 负数表示version1小于version2，0表示相等，正数表示version1大于version2
     */
    public static int compareVersions(String version1, String version2) {
        if (version1 == null && version2 == null) {
            return 0;
        }
        if (version1 == null) {
            return -1;
        }
        if (version2 == null) {
            return 1;
        }

        int[] v1Parts = parseVersionParts(version1);
        int[] v2Parts = parseVersionParts(version2);

        for (int i = 0; i < 3; i++) {
            if (v1Parts[i] != v2Parts[i]) {
                return Integer.compare(v1Parts[i], v2Parts[i]);
            }
        }

        return 0;
    }

    /**
     * 检查版本是否在指定范围内
     *
     * @param version      要检查的版本
     * @param minVersion   最小版本（包含）
     * @param maxVersion   最大版本（不包含）
     * @return 如果版本在范围内则返回true
     */
    public static boolean isVersionInRange(String version, String minVersion, String maxVersion) {
        if (version == null) {
            return false;
        }
        boolean aboveMin = minVersion == null || compareVersions(version, minVersion) >= 0;
        boolean belowMax = maxVersion == null || compareVersions(version, maxVersion) < 0;
        return aboveMin && belowMax;
    }

    /**
     * 解析版本字符串获取主版本号
     *
     * @param version 版本字符串
     * @return 主版本号
     */
    public static int getMajorVersion(String version) {
        return parseVersionParts(version)[0];
    }

    /**
     * 解析版本字符串获取次版本号
     *
     * @param version 版本字符串
     * @return 次版本号
     */
    public static int getMinorVersion(String version) {
        return parseVersionParts(version)[1];
    }

    /**
     * 解析版本字符串获取补丁版本号
     *
     * @param version 版本字符串
     * @return 补丁版本号
     */
    public static int getPatchVersion(String version) {
        return parseVersionParts(version)[2];
    }

    /**
     * 获取版本的预发布标签
     *
     * @param version 版本字符串
     * @return 预发布标签，如果没有则返回null
     */
    public static String getPreRelease(String version) {
        if (version == null) {
            return null;
        }
        Matcher matcher = VERSION_PATTERN.matcher(version.trim());
        if (matcher.matches()) {
            return matcher.group(4);
        }
        return null;
    }

    private static int[] parseVersionParts(String version) {
        int[] parts = {0, 0, 0};
        if (version == null) {
            return parts;
        }

        Matcher matcher = VERSION_PATTERN.matcher(version.trim());
        if (matcher.matches()) {
            parts[0] = Integer.parseInt(matcher.group(1));
            parts[1] = Integer.parseInt(matcher.group(2));
            parts[2] = Integer.parseInt(matcher.group(3));
        }
        return parts;
    }
}
