package io.opencti.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 重写自: opencti-graphql/src/utils/syntax.js
 * STIX语法工具类
 *
 * TODO: 依赖 STIX Pattern 解析器（Phase 3.2 ANTLR实现）
 */
public final class SyntaxUtils {

    public static final String STIX_PATTERN_TYPE = "stix";

    private SyntaxUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/syntax.js - extractObservablesFromIndicatorPattern
     * 从Indicator Pattern提取Observable
     *
     * TODO: 需要ANTLR STIX Pattern解析器实现
     *
     * @param pattern STIX Pattern字符串
     * @return Observable列表
     */
    public static List<Map<String, Object>> extractObservablesFromIndicatorPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return Collections.emptyList();
        }

        // TODO: 实现ANTLR解析器后完善此方法
        // 当前返回空列表，待Phase 3.2实现STIX Pattern解析器后完善
        return Collections.emptyList();
    }

    /**
     * 重写自: opencti-graphql/src/utils/syntax.js - validateObservableGeneration
     * 验证Observable生成是否有效
     *
     * @param observableType   Observable类型
     * @param indicatorPattern Indicator Pattern
     * @return 如果有效则返回true
     */
    public static boolean validateObservableGeneration(String observableType, String indicatorPattern) {
        if (observableType == null || indicatorPattern == null) {
            return false;
        }

        // 网络流量类型有特殊限制
        if ("Network-Traffic".equals(observableType)) {
            if (indicatorPattern.contains("dst_ref") || indicatorPattern.contains("src_ref")) {
                return false;
            }
        }

        return true;
    }

    /**
     * 重写自: opencti-graphql/src/utils/syntax.js - unflatten
     * 将扁平化的Map转换为嵌套Map
     *
     * @param data 扁平化的数据
     * @return 嵌套的数据
     */
    public static Map<String, Object> unflatten(Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new java.util.HashMap<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String[] keys = key.split("\\.");

            Map<String, Object> current = result;
            for (int i = 0; i < keys.length - 1; i++) {
                String k = keys[i];
                if (!current.containsKey(k)) {
                    current.put(k, new java.util.HashMap<String, Object>());
                }
                Object next = current.get(k);
                if (next instanceof Map) {
                    current = (Map<String, Object>) next;
                } else {
                    break;
                }
            }
            current.put(keys[keys.length - 1], value);
        }
        return result;
    }

    /**
     * 检查是否为STIX Pattern类型
     *
     * @param patternType Pattern类型
     * @return 如果是STIX Pattern则返回true
     */
    public static boolean isStixPatternType(String patternType) {
        return STIX_PATTERN_TYPE.equalsIgnoreCase(patternType);
    }
}
