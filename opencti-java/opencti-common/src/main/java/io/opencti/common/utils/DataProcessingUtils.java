package io.opencti.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 重写自: opencti-graphql/src/utils/data-processing.ts
 * 数据处理工具类
 */
public final class DataProcessingUtils {

    private DataProcessingUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/data-processing.ts - unflatten
     * 将扁平化的Map转换为嵌套Map
     *
     * @param data 扁平化的Map
     * @return 嵌套Map
     */
    public static Map<String, Object> unflatten(Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        if (data == null || data.isEmpty()) {
            return result;
        }

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String[] keys = key.split("\\.");

            Map<String, Object> current = result;
            for (int i = 0; i < keys.length - 1; i++) {
                String k = keys[i];
                if (!current.containsKey(k)) {
                    current.put(k, new HashMap<String, Object>());
                }
                Object next = current.get(k);
                if (next instanceof Map) {
                    current = (Map<String, Object>) next;
                } else {
                    Map<String, Object> newMap = new HashMap<>();
                    current.put(k, newMap);
                    current = newMap;
                }
            }
            current.put(keys[keys.length - 1], value);
        }

        return result;
    }

    /**
     * 重写自: opencti-graphql/src/utils/data-processing.ts - flatten
     * 将嵌套Map转换为扁平化Map
     *
     * @param data 嵌套Map
     * @return 扁平化Map
     */
    public static Map<String, Object> flatten(Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        if (data == null || data.isEmpty()) {
            return result;
        }
        flattenInternal(data, "", result);
        return result;
    }

    private static void flattenInternal(Map<String, Object> data, String prefix, Map<String, Object> result) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                flattenInternal((Map<String, Object>) value, key, result);
            } else {
                result.put(key, value);
            }
        }
    }

    /**
     * 深度合并两个Map
     *
     * @param target 目标Map
     * @param source 源Map
     * @return 合并后的Map
     */
    public static Map<String, Object> deepMerge(Map<String, Object> target, Map<String, Object> source) {
        Map<String, Object> result = new HashMap<>(target);
        if (source == null) {
            return result;
        }

        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            Object sourceValue = entry.getValue();
            Object targetValue = result.get(key);

            if (targetValue instanceof Map && sourceValue instanceof Map) {
                result.put(key, deepMerge((Map<String, Object>) targetValue, (Map<String, Object>) sourceValue));
            } else {
                result.put(key, sourceValue);
            }
        }

        return result;
    }

    /**
     * 从Map中获取嵌套值
     *
     * @param data  Map数据
     * @param path  路径（如 "a.b.c"）
     * @param <T>   返回类型
     * @return 值，如果不存在则返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNestedValue(Map<String, Object> data, String path) {
        if (data == null || path == null || path.isEmpty()) {
            return null;
        }

        String[] keys = path.split("\\.");
        Object current = data;

        for (String key : keys) {
            if (current instanceof Map) {
                current = ((Map<String, Object>) current).get(key);
            } else {
                return null;
            }
        }

        return (T) current;
    }

    /**
     * 设置嵌套值
     *
     * @param data  Map数据
     * @param path  路径
     * @param value 值
     */
    @SuppressWarnings("unchecked")
    public static void setNestedValue(Map<String, Object> data, String path, Object value) {
        if (data == null || path == null || path.isEmpty()) {
            return;
        }

        String[] keys = path.split("\\.");
        Map<String, Object> current = data;

        for (int i = 0; i < keys.length - 1; i++) {
            String key = keys[i];
            Object next = current.get(key);
            if (!(next instanceof Map)) {
                next = new HashMap<String, Object>();
                current.put(key, next);
            }
            current = (Map<String, Object>) next;
        }

        current.put(keys[keys.length - 1], value);
    }
}
